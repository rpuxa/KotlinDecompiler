package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Type

class SetBackingField(var element: Element) : ComplexElement {
    override fun getByIndex(index: Int) = element

    override fun replaceByIndex(index: Int, element: Element) {
        this.element = element
    }

    override val type: Type get() = Type.NO_TYPE

    override val size get() = 1

    override fun render(builder: CodeStringBuilder) {
        builder.append("field = ")
        element.render(builder)
    }
}