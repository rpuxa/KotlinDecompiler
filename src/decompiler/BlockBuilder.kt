package decompiler

import classfile.attribute.codeattribute.Condition
import classfile.attribute.codeattribute.Instruction
import classfile.attribute.codeattribute.InstructionTypes
import classfile.attribute.codeattribute.arguments.ByteArgument
import classfile.attribute.codeattribute.arguments.Jump
import classfile.attribute.codeattribute.arguments.ShortArgument
import classfile.constant.ConstantPool
import classfile.constant.constants.*
import decompiler.elements.*
import decompiler.elements.blocks.DoWhile
import decompiler.elements.blocks.If
import decompiler.elements.blocks.While
import decompiler.elements.literals.*
import pop
import java.util.*
import kotlin.test.fail

object BlockBuilder {

    fun fromCodeSequence(pool: ConstantPool, sequence: ControlList<Instruction>): Block {
        return create(pool, sequence, 0)
    }

    private fun create(pool: ConstantPool, sequence: ControlList<Instruction>, loopsCount: Int): Block {
        var loopsCount = loopsCount
        val stack = Stack<Element>()



        loop@ while (true) {
            val instruction = sequence.currentOrNullAndMoveForward() ?: break
            val arg = instruction.argument
            when (val type = instruction.type) {
                InstructionTypes.ICONST, InstructionTypes.BIPUSH -> {
                    arg as ByteArgument
                    stack.add(IntLiteral(arg.value))
                }

                InstructionTypes.SIPUSH -> {
                    arg as ShortArgument
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

                InstructionTypes.ACONST_NULL -> {
                    stack.push(NullObject)
                }

                InstructionTypes.LDC_W -> {
                    arg as ShortArgument
                    val literal = when (val constant = pool.getConstant(arg.value)) {
                        is IntConstant -> IntLiteral(constant.value)
                        is LongConstant -> LongLiteral(constant.value)
                        is FloatConstant -> FloatLiteral(constant.value)
                        is DoubleConstant -> DoubleLiteral(constant.value)
                        is StringConstant -> StringLiteral(constant.value)
                        else -> fail("Wrong constant $constant")
                    }
                    stack.push(literal)
                }

                InstructionTypes.INVOKEVIRTUAL -> {
                    arg as ShortArgument
                    stack.push(FunctionInvoke.fromStack(pool.get(arg.value), false, stack))
                }

                InstructionTypes.INVOKESTATIC -> {
                    arg as ShortArgument
                    stack.push(FunctionInvoke.fromStack(pool.get(arg.value), true, stack))
                }

                InstructionTypes.GETSTATIC -> {
                    arg as ShortArgument
                    stack.push(GetField.fromStack(pool.get(arg.value), true, stack))
                }

                InstructionTypes.NEW -> {
                    if (sequence.currentAndMoveForward().type != InstructionTypes.DUP)
                        fail()
                }

                InstructionTypes.INVOKESPECIAL -> {
                    arg as ShortArgument
                    stack.push(
                        NewObject.fromStack(
                            pool.get(arg.value),
                            stack
                        )
                    )
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

                InstructionTypes.WHILE_LOOP_CONDITION -> {
                    arg as Jump
                    stack.push(getComparerOperator(arg, stack, true))
                }

                InstructionTypes.IF -> {
                    arg as Jump
                    val start = sequence.indexOf(instruction) + 1
                    do {
                        val (currentType, currentArg) = sequence.currentAndMoveForward()
                    } while (currentType != InstructionTypes.LABEL || (currentArg as ShortArgument).value != arg.labelId)
                    sequence.moveBack(2)
                    val ifBlock: Block
                    val elseBlock: Block?
                    val current = sequence.currentAndMoveForward()
                    if (current.type == InstructionTypes.GOTO_W && (current.argument as Jump).jumpForward) {
                        ifBlock = create(pool, sequence.subSequence(start, sequence.currentPosition - 2), loopsCount)
                        val labelId = current.argument.labelId
                        sequence.currentAndMoveForward()
                        val startElse = sequence.currentPosition
                        do {
                            val (currentType, currentArg) = sequence.currentAndMoveForward()
                        } while (currentType != InstructionTypes.LABEL || (currentArg as ShortArgument).value != labelId)
                        elseBlock = create(
                            pool,
                            sequence.subSequence(startElse, sequence.currentPosition - 1),
                            loopsCount
                        )
                    } else {
                        ifBlock = create(pool, sequence.subSequence(start, sequence.currentPosition - 1), loopsCount)
                        elseBlock = null
                        sequence.moveForward()
                    }

                    val operator = getComparerOperator(arg, stack, true)
                    stack.push(If(operator, ifBlock, elseBlock))
                }

                InstructionTypes.LABEL -> {
                    arg as ShortArgument
                    if (sequence.lastElement())
                        break@loop
                    val start = sequence.currentPosition
                    val labelId = arg.value
                    do {
                        val (currentType, currentArg) = sequence.currentAndMoveForward()
                    } while (
                        (currentType != InstructionTypes.IF && currentType != InstructionTypes.GOTO_W) ||
                        (currentArg as Jump).labelId != labelId
                    )
                    val instructionAfterLoop = sequence.currentOrNull()
                    val labelAfterLoopId = if (instructionAfterLoop?.type == InstructionTypes.LABEL) {
                        (instructionAfterLoop.argument as ShortArgument).value
                    } else {
                        null
                    }
                    sequence.moveBack()
                    val labelJump = sequence.current().argument as Jump
                    sequence.moveBack()
                    val end = sequence.currentPosition
                    var whileLoopHasCondition = false
                    do {
                        val i = sequence.previous()!!
                        val argument = i.argument
                        if (i.type == InstructionTypes.GOTO_W) {
                            val jump = argument as Jump
                            if (jump.labelId == labelId) {
                                sequence.moveForward()
                                sequence.replace(Instruction(InstructionTypes.CONTINUE, ShortArgument(loopsCount)))
                            }
                            if (jump.labelId == labelAfterLoopId) {
                                sequence.moveForward()
                                sequence.replace(Instruction(InstructionTypes.BREAK, ShortArgument(loopsCount)))
                            }
                        } else if (labelJump.condition == Condition.NO_CONDITION &&
                            i.type == InstructionTypes.IF &&
                            (argument as Jump).labelId == labelAfterLoopId
                        ) {
                            whileLoopHasCondition = true
                            sequence.moveForward()
                            sequence.replace(Instruction(InstructionTypes.WHILE_LOOP_CONDITION, argument))
                        }
                    } while (i != instruction)

                    val subSequence = sequence.subSequence(start, end)
                    stack.push(
                        if (labelJump.condition != Condition.NO_CONDITION) {
                            val block = create(pool, subSequence, ++loopsCount)
                            DoWhile(getComparerOperator(labelJump, block.elements, false), block)
                        } else {
                            val block = create(pool, subSequence, ++loopsCount)
                            val condition = if (whileLoopHasCondition) block.elements.removeAt(0) else null
                            While(condition, block)
                        }
                    )
                    sequence.jumpTo(end + 2)
                    if (sequence.currentOrNull()?.type == InstructionTypes.LABEL)
                        sequence.moveForward()
                }

                InstructionTypes.BREAK -> {
                    arg as ShortArgument
                    stack.push(Break(arg.value))
                }

                InstructionTypes.CONTINUE -> {
                    arg as ShortArgument
                    stack.push(Continue(arg.value))
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

    private fun getComparerOperator(jump: Jump, list: MutableList<Element>, reverse: Boolean): Operator {
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
        }

        val secondArgument = when (jump.condition) {
            Condition.IS_ZERO,
            Condition.IS_NOT_ZERO,
            Condition.IS_NOT_NEGATIVE,
            Condition.IS_POSITIVE,
            Condition.IS_NEGATIVE,
            Condition.IS_NOT_POSITIVE -> IntLiteral.ZERO
            Condition.IS_NULL,
            Condition.IS_NOT_NULL -> NullObject
            else -> list.pop()
        }

        return Operator(if (reverse) operatorType.reversed() else operatorType, arrayOf(list.pop(), secondArgument))
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
                    copyOfDeclaredVariables.add(element.variable)
                    element.replaceBy(AssignmentWithDeclaration())
                }
            }

        }*/
}