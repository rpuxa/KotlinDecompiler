package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Type
import decompiler.Variables

class Assignment(val variable: Variable, var element: Element) : ComplexElement {

    override fun render(builder: CodeStringBuilder) {
        builder.append(variable.name)
        builder.append(" = ")
        element.render(builder)
    }

    override fun getByIndex(index: Int) = element

    override fun replaceByIndex(index: Int, element: Element) {
        this.element = element
    }

    override val size get() = 1

    override val type: Type get() = Type.NO_TYPE
}