package decompiler.elements

import classfile.constant.constants.FieldRefConstant
import decompiler.CodeStringBuilder
import decompiler.MemberReference
import decompiler.Type
import java.util.*

class GetField(
    val ref: MemberReference,
    var obj: Element?
) : ComplexElement {

    override fun render(builder: CodeStringBuilder) {
        val obj = obj
        if (obj != null) {
            obj.render(builder)
        } else {
            builder.append(ref.clazz.name)
        }
        builder.append('.')
        builder.append(ref.name)
    }

    override val type = ref.signature.returnType

    override fun getByIndex(index: Int) = obj!!

    override fun replaceByIndex(index: Int, element: Element) {
        obj = element
    }

    override val size get() = if (obj == null) 0 else 1

    companion object {
        fun fromStack(fieldRefConstant: FieldRefConstant, static: Boolean, stack: Stack<Element>): GetField {
            val ref = MemberReference.valueOf(fieldRefConstant)
            val obj = if (static) null else stack.pop()
            return GetField(ref, obj)
        }
    }
}