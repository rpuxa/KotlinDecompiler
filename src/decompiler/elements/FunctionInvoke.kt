package decompiler.elements

import classfile.constant.constants.MethodRefConstant
import decompiler.CodeStringBuilder
import decompiler.MemberReference
import java.util.*

class FunctionInvoke(
    private val ref: MemberReference,
    val arguments: MutableList<Element>,
    private var obj: Element?
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
        renderArguments(builder, arguments)
    }

    override fun getByIndex(index: Int): Element {
        val i = index + if (obj == null) 1 else 0
        return if (i == 0) obj!! else arguments[i - 1]
    }

    override fun replaceByIndex(index: Int, element: Element) {
        val i = index + if (obj == null) 1 else 0
        if (i == 0) {
            obj = element
        } else {
            arguments[i - 1] = element
        }
    }

    override val size get() = (if (obj == null) 0 else 1) + arguments.size

    companion object {
        fun fromStack(methodRefConstant: MethodRefConstant, static: Boolean, stack: Stack<Element>): FunctionInvoke {
            val ref = MemberReference.valueOf(methodRefConstant)
            val arguments = Array(ref.signature.arguments.size) { stack.pop() }
            val obj = if (static) null else stack.pop()

            return FunctionInvoke(ref, arguments.reversed().toMutableList(), obj)
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