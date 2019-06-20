package decompiler.elements.blocks

import decompiler.Block
import decompiler.CodeStringBuilder
import decompiler.Element
import decompiler.elements.SpecialBlock
import decompiler.elements.literals.BooleanLiteral

class While(
    val condition: Element?,
    val block: Block
) : SpecialBlock {
    override fun render(builder: CodeStringBuilder) {
            builder.append("while (")
            (condition ?: BooleanLiteral.TRUE).render(builder)
            builder.append(") {")
            builder.newLine()
            block.render(builder)
            builder.append("}")
            builder.newLine()
    }

    override fun iterator() = block.iterator()
}