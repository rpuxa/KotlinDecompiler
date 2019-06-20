package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Element

class DeclarationAssigment(
    val final: Boolean,
    val showType: Boolean,
    variableIndex: Int,
    element: Element
) : Assignment(variableIndex, element) {

    override fun render(builder: CodeStringBuilder) {
        if (final)
            builder.append("val")
        else
            builder.append("var")
        builder.append(' ')
        super.render(builder)
    }
}