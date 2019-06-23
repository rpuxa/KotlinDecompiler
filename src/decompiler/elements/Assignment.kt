package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.VariablesNames

open class Assignment(val variableIndex: Int, var element: Element, val variableNames: VariablesNames) : ComplexElement {

    override fun render(builder: CodeStringBuilder) {
        builder.append(variableNames[variableIndex])
        builder.append(" = ")
        element.render(builder)
    }

    override fun getByIndex(index: Int) = element

    override fun replaceByIndex(index: Int, element: Element) {
        this.element = element
    }

    override val size get() = 1
}