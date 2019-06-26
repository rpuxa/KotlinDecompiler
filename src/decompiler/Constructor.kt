package decompiler

import classfile.method.Method
import decompiler.elements.Block
import decompiler.elements.Element

class Constructor(
    val method: Method,
    val signature: Signature,
    val block: Block,
    val superArguments: MutableList<Element>,
    val call: Int,
    val variables: Variables
) : Renderable {



    override fun render(builder: CodeStringBuilder) {
        builder.append("constructor(")
        signature.arguments.forEachIndexed { index, argument ->
            if (index != 0)
                builder.append(", ")
            builder.append(variables.getArgumentName(index))
                .append(": ")
                .append(argument.name)
        }
        builder.append(")")
        if (call == CALL_SUPER_CONSTRUCTOR) {
            builder.append(" : super(")
        } else if (call == CALL_THIS_CONSTRUCTOR) {
            builder.append(" : this(")
        }

        if (call != CALL_NOTHING) {
            superArguments.forEachIndexed { index, element ->
                if (index != 0)
                    builder.append(", ")
                builder.append(element)
            }
            builder.append(")")
        }
        builder.append(" {")
            .newLine()
            .append(block)
            .append("}")
            .newLine()
    }

    companion object {
        const val CALL_SUPER_CONSTRUCTOR = 0
        const val CALL_THIS_CONSTRUCTOR = 1
        const val CALL_NOTHING = 2
    }
}