package decompiler

import decompiler.elements.ComplexElement
import decompiler.elements.Element

class ComplexElementListDelegate(val list: MutableList<Element>) : ComplexElement {

    override fun getByIndex(index: Int) = list[index]

    override fun replaceByIndex(index: Int, element: Element) {
        list[index] = element
    }

    override val size get() = list.size

    override fun render(builder: CodeStringBuilder) {
        throw UnsupportedOperationException()
    }
}