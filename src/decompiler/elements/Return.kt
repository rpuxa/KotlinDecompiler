package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Type

class Return(var element: Element?) : ComplexElement {

    override fun render(builder: CodeStringBuilder) {
        builder.append("return")
        if (element == null)
            return
        builder.append(' ')
        element!!.render(builder)
    }

    override val type: Type get() = Type.NOTHING

    override fun getByIndex(index: Int): Element = element!!

    override fun replaceByIndex(index: Int, element: Element) {
        this.element = element
    }

    override val size get() = if (element == null) 0 else 1
}