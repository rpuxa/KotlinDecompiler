package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Element

class DeclarationVariable(val final: Boolean, val showType: Boolean, val variableIndex: Int) : Element {

    override fun render(builder: CodeStringBuilder) {
        if (final)
            builder.append("val")
        else
            builder.append("var")

        builder.append(' ').append(Names.variable(variableIndex))
    }
}