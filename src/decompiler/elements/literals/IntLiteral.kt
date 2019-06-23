package decompiler.elements.literals

import decompiler.CodeStringBuilder
import decompiler.elements.Element

class IntLiteral(val value: Int) : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append(value)
    }

    companion object {
        @JvmField val ZERO = IntLiteral(0)
    }
}