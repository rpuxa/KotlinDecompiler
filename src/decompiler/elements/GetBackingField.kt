package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Type

class GetBackingField(override val type: Type) : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append("field")
    }
}