package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Element

class Break(val loop: Int) : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append("break")
    }
}