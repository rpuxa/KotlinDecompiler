package decompiler.elements

import classfile.constant.constants.FieldRefConstant
import classfile.constant.constants.MethodRefConstant
import decompiler.CodeStringBuilder
import decompiler.Element
import decompiler.MemberReference
import java.util.*

class GetField(
    val ref: MemberReference,
    val obj: Element?
) : Element {

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

    companion object {
        fun fromStack(fieldRefConstant: FieldRefConstant, static: Boolean, stack: Stack<Element>): GetField {
            val ref = MemberReference.valueOf(fieldRefConstant)
            val obj = if (static) null else stack.pop()
            return GetField(ref, obj)
        }
    }
}