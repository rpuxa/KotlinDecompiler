package decompiler

import classfile.attribute.codeattribute.InstructionTypes
import decompiler.elements.ComplexElement
import decompiler.elements.Element

class Operator(val operatorType: OperatorType, private val operands: MutableList<Element>) :
    ComplexElement by ComplexElementListDelegate(operands) {

    override fun render(builder: CodeStringBuilder) {

        fun renderOperand(operand: Element) {
            if (operand !is Operator || operand.operatorType.priority >= operatorType.priority) {
                operand.render(builder)
                return
            }

            builder.append('(')
            operand.render(builder)
            builder.append(')')
        }

        when (operatorType) {
            is BinaryOperator -> {
                renderOperand(operands[0])
                builder.append(' ')
                    .append(operatorType.sign)
                    .append(' ')
                renderOperand(operands[1])
            }
        }
    }

    override val type: Type
        get() = operatorType.type
}

sealed class OperatorType(val priority: Int, val arity: Int, val type: Type)

open class BinaryOperator(priority: Int, val sign: String, type: Type) : OperatorType(priority, 2, type)

abstract class ComparatorOperator(priority: Int, sign: String) : BinaryOperator(priority, sign, Type.BOOLEAN) {
    abstract fun reversed(): ComparatorOperator
}

private const val ADD_PRIORITY = 0
private const val MUL_PRIORITY = 1

private const val ADD_SIGN = "+"
private const val SUB_SIGN = "-"
private const val MUL_SIGN = "*"
private const val DIV_SIGN = "/"

@JvmField
val OPERATORS: Map<InstructionTypes, OperatorType> = mapOf(
    InstructionTypes.IADD to BinaryOperator(ADD_PRIORITY, ADD_SIGN, Type.INT),
    InstructionTypes.LADD to BinaryOperator(ADD_PRIORITY, ADD_SIGN, Type.LONG),
    InstructionTypes.FADD to BinaryOperator(ADD_PRIORITY, ADD_SIGN, Type.FLOAT),
    InstructionTypes.DADD to BinaryOperator(ADD_PRIORITY, ADD_SIGN, Type.DOUBLE),

    InstructionTypes.ISUB to BinaryOperator(ADD_PRIORITY, SUB_SIGN, Type.INT),
    InstructionTypes.LSUB to BinaryOperator(ADD_PRIORITY, SUB_SIGN, Type.LONG),
    InstructionTypes.FSUB to BinaryOperator(ADD_PRIORITY, SUB_SIGN, Type.FLOAT),
    InstructionTypes.DSUB to BinaryOperator(ADD_PRIORITY, SUB_SIGN, Type.DOUBLE),

    InstructionTypes.IMUL to BinaryOperator(MUL_PRIORITY, MUL_SIGN, Type.INT),
    InstructionTypes.LMUL to BinaryOperator(MUL_PRIORITY, MUL_SIGN, Type.LONG),
    InstructionTypes.FMUL to BinaryOperator(MUL_PRIORITY, MUL_SIGN, Type.FLOAT),
    InstructionTypes.DMUL to BinaryOperator(MUL_PRIORITY, MUL_SIGN, Type.DOUBLE),

    InstructionTypes.IDIV to BinaryOperator(MUL_PRIORITY, DIV_SIGN, Type.INT),
    InstructionTypes.LDIV to BinaryOperator(MUL_PRIORITY, DIV_SIGN, Type.LONG),
    InstructionTypes.FDIV to BinaryOperator(MUL_PRIORITY, DIV_SIGN, Type.FLOAT),
    InstructionTypes.DDIV to BinaryOperator(MUL_PRIORITY, DIV_SIGN, Type.DOUBLE)
)

@JvmField
val OPERATORS_INSTRUCTIONS = OPERATORS.keys

object Equally : ComparatorOperator(-10, "==") {
    override fun reversed() = NotEqually
}

object NotEqually : ComparatorOperator(-10, "!=") {
    override fun reversed() = Equally
}

object Less : ComparatorOperator(-10, "<") {
    override fun reversed() = GreaterOrEqual
}

object Greater : ComparatorOperator(-10, ">") {
    override fun reversed() = LessOrEqual
}

object GreaterOrEqual : ComparatorOperator(-10, ">=") {
    override fun reversed() = Less
}

object LessOrEqual : ComparatorOperator(-10, "<=") {
    override fun reversed() = Greater
}
