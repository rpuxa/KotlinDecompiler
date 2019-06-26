package decompiler.elements.literals

import decompiler.CodeStringBuilder
import decompiler.Type
import decompiler.elements.Element

class BooleanLiteral private constructor(val value: Boolean) : Element {

    override fun render(builder: CodeStringBuilder) {
        builder.append(value)
    }

    override val type get() = Type.BOOLEAN

    companion object {
        @JvmField val TRUE = BooleanLiteral(true)
        @JvmField val FALSE = BooleanLiteral(false)

        @JvmName("valueOf")
        operator fun invoke(value: Boolean) = if (value) TRUE else FALSE
    }
}