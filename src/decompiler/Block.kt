package decompiler

import decompiler.elements.ElementSequence
import java.util.*

class Block(val elements: Stack<Element>) : ElementSequence {

    override fun render(builder: CodeStringBuilder) {
        builder.addTab()
        elements.forEach {
            it.render(builder)
            builder.newLine()
        }
        builder.removeTab()
    }

    override fun iterator() = elements.iterator()
}