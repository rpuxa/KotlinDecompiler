package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Type
import decompiler.Variables

class DeclarationAssignment(
    var variable: Variable,
    var element: Element,
    var final: Boolean = false,
    var showType: Boolean = false
) : ComplexElement {

    override fun render(builder: CodeStringBuilder) {
        if (final)
            builder.append("val")
        else
            builder.append("var")
        builder.append(' ')
            .append(variable.name)
        if (showType) {
            builder.append(": ")
                .append(variable.type)
        }
        builder.append(" = ")
            .append(element)
    }

    override fun getByIndex(index: Int) = element

    override fun replaceByIndex(index: Int, element: Element) {
        this.element = element
    }

    override val size get() = 1

    override val type: Type get() = Type.NO_TYPE
}