package decompiler.elements.literals

import decompiler.CodeStringBuilder
import decompiler.Type
import decompiler.elements.Element

class IntLiteral(val value: Int) : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append(value)
    }

    override val type get() = Type.INT

    companion object {
        @JvmField val ZERO = IntLiteral(0)
    }
}