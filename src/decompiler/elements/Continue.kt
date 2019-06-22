package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Element

class Continue(val loop: Int, var showLabel: Boolean = true) : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append("continue")
        if (showLabel)
            builder.append("@${Names.loopLabel(loop)}")
    }
}