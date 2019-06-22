package decompiler.elements

import classfile.constant.constants.MethodRefConstant
import decompiler.CodeStringBuilder
import decompiler.Element
import decompiler.MemberReference
import java.util.*

class NewObject(
    val constructor: MemberReference,
    val arguments: List<Element>
) : Element {
    override fun render(builder: CodeStringBuilder) {
        builder.append(constructor.clazz.name)
        FunctionInvoke.renderArguments(builder, arguments)
    }

    companion object {
        fun fromStack(methodRefConstant: MethodRefConstant, stack: Stack<Element>): NewObject {
            val ref = MemberReference.valueOf(methodRefConstant)
            val arguments = Array(ref.arguments.size) { stack.pop() }

            return NewObject(ref, arguments.reversed())
        }
    }
}