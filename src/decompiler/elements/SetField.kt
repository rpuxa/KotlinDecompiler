package decompiler.elements

import classfile.constant.constants.FieldRefConstant
import decompiler.CodeStringBuilder
import decompiler.MemberReference
import decompiler.Type
import java.util.*

class SetField(
    val ref: MemberReference,
    var obj: Element?,
    val element: Element
) : ComplexElement {

    override fun render(builder: CodeStringBuilder) {
        val obj = obj
        if (obj != null) {
            obj.render(builder)
        } else {
            builder.append(ref.clazz.name)
        }
        builder.append('.')
            .append(ref.name)
            .append(" = ")
        element.render(builder)
    }

    override fun getByIndex(index: Int) = obj!!

    override fun replaceByIndex(index: Int, element: Element) {
        obj = element
    }

    override val type: Type get() = Type.NO_TYPE

    override val size get() = if (obj == null) 0 else 1

    companion object {
        fun fromStack(fieldRefConstant: FieldRefConstant, static: Boolean, stack: Stack<Element>): SetField {
            val ref = MemberReference.valueOf(fieldRefConstant)
            val element = stack.pop()
            val obj = if (static) null else stack.pop()
            return SetField(ref, obj, element)
        }
    }
}