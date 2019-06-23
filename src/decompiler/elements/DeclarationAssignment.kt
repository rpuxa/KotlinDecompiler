package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.VariablesNames

class DeclarationAssignment(
    val final: Boolean,
    val showType: Boolean,
    variableIndex: Int,
    element: Element,
    variablesNames: VariablesNames
) : Assignment(variableIndex, element, variablesNames) {

    override fun render(builder: CodeStringBuilder) {
        if (final)
            builder.append("val")
        else
            builder.append("var")
        builder.append(' ')
        super.render(builder)
    }
}