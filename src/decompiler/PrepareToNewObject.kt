package decompiler

import decompiler.elements.Element

object PrepareToNewObject : Element {
    override fun render(builder: CodeStringBuilder) {
        throw UnsupportedOperationException()
    }

    override val type get() = throw UnsupportedOperationException()
}