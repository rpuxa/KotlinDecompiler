package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Element

class Variable(val index: Int) : Element {

    override fun render(builder: CodeStringBuilder) {
        builder.append(Names.variable(index))

    }
}