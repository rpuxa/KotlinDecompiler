package decompiler

import classfile.attribute.codeattribute.InstructionTypes

class Operator(val type: OperatorType, private val operands: Array<Element>) : Element {

    override fun render(builder: CodeStringBuilder) {

        fun renderOperand(operand: Element) {
            if (operand !is Operator || operand.type.priority >= type.priority) {
                operand.render(builder)
                return
            }

            builder.append('(')
            operand.render(builder)
            builder.append(')')
        }

        when (type) {
            is BinaryOperator -> {
                renderOperand(operands[0])
                builder.append(' ')
                    .append(type.sign)
                    .append(' ')
                renderOperand(operands[1])
            }
        }
    }
}

sealed class OperatorType(val priority: Int, val arity: Int)

open class BinaryOperator(priority: Int, val sign: String) :
    OperatorType(priority, 2)

abstract class ComparatorOperator(priority: Int, sign: String) : BinaryOperator(priority, sign) {
    abstract fun reversed(): ComparatorOperator
}

@JvmField
val OPERATORS: Map<InstructionTypes, OperatorType> = mapOf(
    InstructionTypes.ADD to BinaryOperator(0, "+"),
    InstructionTypes.SUB to BinaryOperator(0, "-"),
    InstructionTypes.MUL to BinaryOperator(1, "*"),
    InstructionTypes.DIV to BinaryOperator(1, "/")
)

@JvmField
val OPERATORS_INSTRUCTIONS = OPERATORS.keys

object Equally : ComparatorOperator(-10, "==") {
    override fun reversed() = NotEqually
}
object NotEqually : ComparatorOperator(-10, "!="){
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
