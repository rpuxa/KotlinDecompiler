package decompiler.elements

import decompiler.CodeStringBuilder

object GetBackingField : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append("field")
    }
}