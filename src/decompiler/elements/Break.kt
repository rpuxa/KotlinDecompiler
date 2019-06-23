package decompiler.elements

import decompiler.CodeStringBuilder

class Break(val loop: Int, var showLabel: Boolean = true) : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append("break")
        if (showLabel)
            builder.append("@${Names.loopLabel(loop)}")
    }
}