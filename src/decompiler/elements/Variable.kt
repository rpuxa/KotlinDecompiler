package decompiler.elements

import decompiler.CodeStringBuilder
import decompiler.Type

class Variable(
    var name: String,
    val index: Int
) : Element {

    override lateinit var type: Type
        private set

    var usedTimes = 0

    private val types = ArrayList<Type>()

    override fun render(builder: CodeStringBuilder) {
        builder.append(name)
    }

    fun addType(type: Type) {
        types.add(type)
        this.type = Type.mergeTypes(types)
    }
}