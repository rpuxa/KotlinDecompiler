package decompiler

import classfile.ClassFile
import classfile.attribute.codeattribute.Condition
import classfile.attribute.codeattribute.Instruction
import classfile.attribute.codeattribute.InstructionTypes
import classfile.attribute.codeattribute.arguments.ByteArgument
import classfile.attribute.codeattribute.arguments.Jump
import classfile.attribute.codeattribute.arguments.ShortArgument
import classfile.constant.constants.*
import decompiler.elements.Assignment
import decompiler.elements.Return
import decompiler.elements.Variable
import decompiler.elements.blocks.DoWhile
import decompiler.elements.blocks.If
import decompiler.elements.blocks.While
import decompiler.elements.literals.*
import java.util.*
import kotlin.test.fail

object BlockBuilder {

    fun fromCodeSequence(classFile: ClassFile, sequence: InstructionSequence): Block {
        return create(classFile, sequence, 0)
    }

    private fun create(classFile: ClassFile, sequence: InstructionSequence, loopsCount: Int): Block {
        var loopsCount = loopsCount
        val stack = Stack<Element>()



        loop@ while (true) {
            val instruction = sequence.next() ?: break
            val arg = instruction.argument
            when (val type = instruction.type) {
                InstructionTypes.ICONST, InstructionTypes.BIPUSH -> {
                    arg as ByteArgument
                    stack.add(IntLiteral(arg.value))
                }

                InstructionTypes.LCONST -> {
                    arg as ByteArgument
                    stack.add(LongLiteral(arg.value.toLong()))
                }

                InstructionTypes.FCONST -> {
                    arg as ByteArgument
                    stack.add(FloatLiteral(arg.value.toFloat()))
                }

                InstructionTypes.DCONST -> {
                    arg as ByteArgument
                    stack.add(DoubleLiteral(arg.value.toDouble()))
                }

                InstructionTypes.LOAD -> {
                    arg as ByteArgument
                    stack.add(Variable(arg.value))
                }

                InstructionTypes.LDC_W -> {
                    arg as ShortArgument
                    val literal = when (val constant = classFile.constantPool.getConstant(arg.value)) {
                        is IntConstant -> IntLiteral(constant.value)
                        is LongConstant -> LongLiteral(constant.value)
                        is FloatConstant -> FloatLiteral(constant.value)
                        is DoubleConstant -> DoubleLiteral(constant.value)
                        is StringConstant -> StringLiteral(constant.value)
                        else -> fail("Wrong constant $constant")
                    }
                    stack.push(literal)
                }

                in OPERATORS_INSTRUCTIONS -> {
                    val operatorType = OPERATORS[type] ?: error("Unknown operator")
                    val args = Array(operatorType.arity) { stack.pop() }
                    stack.add(Operator(operatorType, args.reversedArray()))

                }

                InstructionTypes.STORE -> {
                    arg as ByteArgument
                    val element = stack.pop()
                    stack.push(Assignment(arg.value, element))
                }

                InstructionTypes.RETURN -> {
                    stack.push(Return(null))
                }

                InstructionTypes.IF -> {
                    arg as Jump
                    val start = sequence.indexOf(instruction) + 1
                    do {
                        val (currentType, currentArg) = sequence.next()!!
                    } while (currentType != InstructionTypes.LABEL || (currentArg as ShortArgument).value != arg.labelId)
                    sequence.moveBack(2)
                    val ifBlock: Block
                    val elseBlock: Block?
                    val current = sequence.next()!!
                    if (current.type == InstructionTypes.GOTO_W) {
                        ifBlock =
                            create(classFile, sequence.subSequence(start, sequence.currentPosition - 2), loopsCount)
                        val conditionalJump = current.argument as Jump
                        val labelId = conditionalJump.labelId
                        sequence.next()
                        val startElse = sequence.currentPosition
                        do {
                            val (currentType, currentArg) = sequence.next()!!
                        } while (currentType != InstructionTypes.LABEL || (currentArg as ShortArgument).value != labelId)
                        elseBlock =
                            create(classFile, sequence.subSequence(startElse, sequence.currentPosition - 1), loopsCount)
                    } else {
                        ifBlock =
                            create(classFile, sequence.subSequence(start, sequence.currentPosition - 1), loopsCount)
                        elseBlock = null
                    }

                    val operator = getComparerOperator(arg, stack)
                    stack.push(If(operator, ifBlock, elseBlock))
                }

                InstructionTypes.LABEL -> {
                    arg as ShortArgument
                    if (sequence.lastElement())
                        break@loop
                    val start = sequence.currentPosition
                    val labelId = arg.value
                    do {
                        val (currentType, currentArg) = sequence.next()!!
                    } while (
                        (currentType != InstructionTypes.IF && currentType != InstructionTypes.GOTO_W) ||
                        (currentArg as Jump).labelId != labelId
                    )
                    val instructionAfterLoop = sequence.current()
                    val labelAfterLoopId = if (instructionAfterLoop.type == InstructionTypes.LABEL) {
                        (instructionAfterLoop.argument as Jump).labelId
                    } else {
                        null
                    }
                    sequence.moveBack()
                    val jump = sequence.current().argument as Jump
                    sequence.moveBack()
                    val end = sequence.currentPosition
                    do {
                        val i = sequence.previous()!!
                        if (i.type == InstructionTypes.GOTO_W) {
                            val jump = i.argument as Jump
                            if (jump.labelId == labelId) {
                                sequence.moveBack()
                                sequence.replace(Instruction(InstructionTypes.CONTINUE, ShortArgument(loopsCount)))
                            }
                            if (jump.labelId == labelAfterLoopId) {
                                sequence.moveBack()
                                sequence.replace(Instruction(InstructionTypes.BREAK, ShortArgument(loopsCount)))
                            }
                        }
                    } while (i != instruction)

                    val subSequence = sequence.subSequence(start, end)
                    stack.push(
                        if (jump.condition != Condition.NO_CONDITION) {
                            val block = create(classFile, subSequence, ++loopsCount)
                            DoWhile(getComparerOperator(jump, stack), block)
                        } else {
                            While(null, create(classFile, subSequence, ++loopsCount))
                        }
                    )
                }


                InstructionTypes.NOP -> {
                    /* ignore */
                }

                else -> fail("Unknown instruction $type")
            }
        }

        //   addVariableDeclarations(stack)

        return Block(stack)
    }

    private fun getComparerOperator(jump: Jump, stack: Stack<Element>): Operator {
        val operatorType = when (jump.condition) {
            Condition.EQUAL_REFERENCES -> Equally
            Condition.NOT_EQUAL_REFERENCES -> NotEqually
            Condition.EQUAL_INTS -> Equally
            Condition.NOT_EQUAL_INTS -> NotEqually
            Condition.GREATER_OR_EQUAL_INT -> GreaterOrEqual
            Condition.GREATER_INT -> Greater
            Condition.LESS_OR_EQUAL_INT -> LessOrEqual
            Condition.LESS_INT -> Less
            Condition.IS_ZERO -> Equally
            Condition.IS_NOT_ZERO -> NotEqually
            Condition.IS_NOT_NEGATIVE -> GreaterOrEqual
            Condition.IS_POSITIVE -> Greater
            Condition.IS_NEGATIVE -> Less
            Condition.IS_NOT_POSITIVE -> LessOrEqual
            Condition.IS_NULL -> Equally
            Condition.IS_NOT_NULL -> NotEqually
            else -> fail()
        }.reversed()

        val secondArgument = when (jump.condition) {
            Condition.IS_ZERO,
            Condition.IS_NOT_ZERO,
            Condition.IS_NOT_NEGATIVE,
            Condition.IS_POSITIVE,
            Condition.IS_NEGATIVE,
            Condition.IS_NOT_POSITIVE -> IntLiteral.ZERO
            Condition.IS_NULL,
            Condition.IS_NOT_NULL -> NullObject
            else -> stack.pop()
        }

        return Operator(operatorType, arrayOf(stack.pop(), secondArgument))
    }

/*
        private fun addVariableDeclarations(block: List<Element>) {

            val hashBlockAssignment = HashMap<Block, BooleanArray>()

           val copyOfDeclaredVariables = declaredVariables
            block.forEach { element ->
                if (element is Block) {
                    addVariableDeclarations(element, copyOfDeclaredVariables)
                }
                if (element is Assignment) {
                    copyOfDeclaredVariables.add(element.variableName)
                    element.replaceBy(AssignmentWithDeclaration())
                }
            }

        }*/
}