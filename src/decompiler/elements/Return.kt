package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Element

class Return(private val element: Element?) : Element {

    override fun render(builder: CodeStringBuilder) {
        builder.append("return")
        if (element == null)
            return
        builder.append(' ')
        element.render(builder)
    }
}