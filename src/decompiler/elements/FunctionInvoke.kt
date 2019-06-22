package decompiler.elements

import classfile.constant.constants.MethodRefConstant
import decompiler.CodeStringBuilder
import decompiler.Element
import decompiler.MemberReference
import java.util.*

class FunctionInvoke(
    private val ref: MemberReference,
    val arguments: List<Element>,
    private val obj: Element?
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
        renderArguments(builder, arguments)
    }

    companion object {
        fun fromStack(methodRefConstant: MethodRefConstant, static: Boolean, stack: Stack<Element>): FunctionInvoke {
            val ref = MemberReference.valueOf(methodRefConstant)
            val arguments = Array(ref.arguments.size){ stack.pop() }
            val obj = if (static) null else stack.pop()

            return FunctionInvoke(ref, arguments.reversed(), obj)
        }

        fun renderArguments(builder: CodeStringBuilder, arguments: List<Element>) {
            builder.append('(')
            arguments.forEachIndexed { index, element ->
                if (index != 0)
                    builder.append(", ")
                element.render(builder)
            }
            builder.append(')')
        }
    }
}