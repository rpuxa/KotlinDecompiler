package decompiler.elements.literals

import decompiler.CodeStringBuilder
import decompiler.Type
import decompiler.elements.Element

class DoubleLiteral(val value: Double) : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append(value)
    }

    override val type get() = Type.DOUBLE
}