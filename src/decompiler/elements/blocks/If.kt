package decompiler.elements.blocks

import decompiler.elements.Block
import decompiler.CodeStringBuilder
import decompiler.Type
import decompiler.elements.Element
import decompiler.elements.SpecialBlock

class If(
    private var condition: Element,
    private var ifBlock: Block,
    private var elseBlock: Block? = null
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

    override fun getByIndex(index: Int) = when (index) {
        0 -> condition
        1 -> ifBlock
        2 -> elseBlock!!
        else -> outOfBound(index)
    }

    override fun replaceByIndex(index: Int, element: Element) {
        when (index) {
            0 -> condition = element
            1 -> ifBlock = element as Block
            2 -> elseBlock = element as Block
        }
    }

    override val size get() = if (elseBlock == null) 2 else 3

    override val type get() = if (elseBlock == null) Type.NO_TYPE else Type.mergeTypes(listOf(ifBlock.type, elseBlock!!.type))
}