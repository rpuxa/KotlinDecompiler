package decompiler.elements.blocks

import decompiler.elements.Block
import decompiler.CodeStringBuilder
import decompiler.elements.Element
import decompiler.elements.SpecialBlock
import decompiler.elements.literals.BooleanLiteral

class While(
    var condition: Element,
    var block: Block
) : SpecialBlock {
    override fun render(builder: CodeStringBuilder) {
        builder.append("while (")
        condition.render(builder)
        builder.append(") {")
        builder.newLine()
        block.render(builder)
        builder.append("}")
        builder.newLine()
    }

    override fun getByIndex(index: Int) = if (index == 0) {
        condition
    } else {
        block
    }

    override fun replaceByIndex(index: Int, element: Element) {
        if (index == 0)
            condition = element
        else
            block = element as Block
    }

    override val size get() = 2
}