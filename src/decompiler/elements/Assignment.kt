package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Element

open class Assignment(val variableIndex: Int, val element: Element) : Element {

    override fun render(builder: CodeStringBuilder) {
        builder.append(Names.variable(variableIndex))
        builder.append(" = ")
        element.render(builder)
    }
}