package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Type

class TempVariable(val index: Int) : Element {
    override val type get() = throw UnsupportedOperationException()

    override fun render(builder: CodeStringBuilder) {
        throw UnsupportedOperationException()
    }
}