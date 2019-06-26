package decompiler.elements.literals

import decompiler.CodeStringBuilder
import decompiler.Type
import decompiler.elements.Element

object NullObject : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append("null")
    }

    override val type get() = Type.NULLABLE_NOTHING
}