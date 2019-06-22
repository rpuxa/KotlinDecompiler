package decompiler.elements.blocks

import decompiler.Block
import decompiler.CodeStringBuilder
import decompiler.Element
import decompiler.elements.SpecialBlock

class DoWhile(
    private var condition: Element,
    private val block: Block
) : SpecialBlock {
    override fun render(builder: CodeStringBuilder) {
        builder.append("do {")
        builder.newLine()
        block.render(builder)
        builder.append("} while (")
        condition.render(builder)
        builder.append(')')
        builder.newLine()
    }

    override val blocks get() = listOf(block)
}