package decompiler.elements.literals

import decompiler.CodeStringBuilder
import decompiler.elements.Element

object NullObject : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append("null")
    }
}