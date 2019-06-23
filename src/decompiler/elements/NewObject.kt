package decompiler.elements

import classfile.constant.constants.MethodRefConstant
import decompiler.CodeStringBuilder
import decompiler.ComplexElementListDelegate
import decompiler.MemberReference
import java.util.*

class NewObject(
    val constructor: MemberReference,
    val arguments: MutableList<Element>
) : ComplexElement by ComplexElementListDelegate(arguments) {
    override fun render(builder: CodeStringBuilder) {
        builder.append(constructor.clazz.name)
        FunctionInvoke.renderArguments(builder, arguments)
    }

    companion object {
        fun fromStack(methodRefConstant: MethodRefConstant, stack: Stack<Element>): NewObject {
            val ref = MemberReference.valueOf(methodRefConstant)
            val arguments = Array(ref.signature.arguments.size) { stack.pop() }

            return NewObject(ref, arguments.reversed().toMutableList())
        }
    }
}