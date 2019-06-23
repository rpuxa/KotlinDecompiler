package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.VariablesNames

class Variable(val index: Int, private val variableNames: VariablesNames) : Element {

    override fun render(builder: CodeStringBuilder) {
        builder.append(variableNames[index])

    }
}