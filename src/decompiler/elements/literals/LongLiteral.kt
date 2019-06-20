package decompiler.elements.literals

import decompiler.CodeStringBuilder
import decompiler.Element

class LongLiteral(val value: Long) : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append(value)
    }
}