package decompiler

import classfile.constant.constants.MethodRefConstant
import decompiler.elements.ComplexElement
import decompiler.elements.Element
import decompiler.elements.FunctionInvoke
import java.util.*

class CallSuperConstructor(
    val reference: MemberReference,
    val arguments: MutableList<Element>
) : ComplexElement by ComplexElementListDelegate(arguments) {
    override fun render(builder: CodeStringBuilder) {
        throw UnsupportedOperationException()
    }

    companion object {
        fun fromStack(methodRefConstant: MethodRefConstant, stack: Stack<Element>): CallSuperConstructor {
            val ref = MemberReference.valueOf(methodRefConstant)
            val arguments = Array(ref.signature.arguments.size) { stack.pop() }

            return CallSuperConstructor(ref, arguments.reversed().toMutableList())
        }
    }
}