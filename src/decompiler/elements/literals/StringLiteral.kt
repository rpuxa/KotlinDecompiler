package decompiler.elements.literals

import decompiler.CodeStringBuilder
import decompiler.elements.Element

class StringLiteral(val value: String) : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append('\"').append(value).append('\"')
    }
}