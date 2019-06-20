package decompiler.elements.literals

import decompiler.CodeStringBuilder
import decompiler.Element

class FloatLiteral(val value: Float) : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append("${value}f")
    }
}