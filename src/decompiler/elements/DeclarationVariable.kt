package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.VariablesNames

class DeclarationVariable(
    val final: Boolean,
    val showType: Boolean,
    val variableIndex: Int,
    private val variablesNames: VariablesNames
) :
    Element {

    override fun render(builder: CodeStringBuilder) {
        if (final)
            builder.append("val")
        else
            builder.append("var")

        builder.append(' ').append(variablesNames[variableIndex])
    }
}