package decompiler.elements

import decompiler.CodeStringBuilder

class TempAssignment(
    val index: Int,
    val element: Element,
    val byteNumber: Int,
    val nextInstructionsByteNumber: Int
) : Element {
    override val type get() = throw UnsupportedOperationException()

    override fun render(builder: CodeStringBuilder) {
        throw UnsupportedOperationException()
    }
}