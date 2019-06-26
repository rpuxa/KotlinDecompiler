package decompiler.elements

import decompiler.ClassReference
import decompiler.CodeStringBuilder
import decompiler.Type

class Cast(var element: Element, val ref: ClassReference) : ComplexElement {

    override val type get() = Type(ref, element.type.nullable)

    override fun getByIndex(index: Int) = element

    override fun replaceByIndex(index: Int, element: Element) {
        this.element = element
    }

    override val size get() = 1


    override fun render(builder: CodeStringBuilder) {
        builder.append(element)
            .append(" as ")
            .append(ref)
    }
}