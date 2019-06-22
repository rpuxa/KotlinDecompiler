package decompiler.elements.blocks

import decompiler.Block
import decompiler.CodeStringBuilder
import decompiler.Element
import decompiler.elements.SpecialBlock
import hasSingleElement

class If(
    private val condition: Element,
    private val ifBlock: Block,
    private val elseBlock: Block? = null
) : SpecialBlock {
    override fun render(builder: CodeStringBuilder) {
        builder.append("if (")
        condition.render(builder)
        builder.append(") {")
        builder.newLine()
        ifBlock.render(builder)
        builder.append('}')
        elseBlock?.let {
            builder.append(" else {")
            builder.newLine()
            it.render(builder)
            builder.append('}')
        }
    }

    override val blocks get() = if (elseBlock == null) listOf(ifBlock) else listOf(ifBlock, elseBlock)
}