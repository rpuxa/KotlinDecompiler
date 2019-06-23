package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.ControlList

class Block(val elements: MutableList<Element>) : ElementSequence {

    override fun render(builder: CodeStringBuilder) {
        builder.addTab()
        elements.forEach {
            builder.append(it)
                .newLine()
        }
        builder.removeTab()
    }

    override fun insert(index: Int, element: Element) {
        elements.add(index, element)
    }

    override fun delete(index: Int) {
        elements.removeAt(index)
    }

    override fun getByIndex(index: Int) = elements[index]

    override fun replaceByIndex(index: Int, element: Element) {
        elements[index] = element
    }

    override val size get() = elements.size
}