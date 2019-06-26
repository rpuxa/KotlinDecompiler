package decompiler

import classfile.constant.ConstantPool
import classfile.method.Method
import decompiler.elements.Block

class KotlinFunction(
    val method: Method,
    val code: Block,
    val variables: Variables,
    val signature: Signature
) : Renderable {


    override fun render(builder: CodeStringBuilder) {
        builder.append("fun ")
            .append(method.name.value)
            .append('(')
        signature.arguments.forEachIndexed { index, argument ->
            if (index != 0) {
                builder.append(", ")
            }
            builder.append(variables.getArgumentName(index))
                .append(": ")
                .append(argument.name)
        }
        builder.append(')')
        if (signature.returnType != Type.UNIT) {
            builder.append(": ")
                .append(signature.returnType.name)
        }
        builder.append(" {")
            .newLine()

        code.render(builder)

        builder.append('}')
            .newLine()
    }

    companion object {
        fun fromMethod(method: Method, pool: ConstantPool): KotlinFunction {
            val signature = Signature.fromDescriptor(method.descriptor.value)
            val codeAttribute = method.attributes.codeAttribute
            val variablesNames = Variables(signature.arity, codeAttribute.attributes.getAttribute()!!)
            return KotlinFunction(
                method,
                BlockBuilder.fromCodeSequence(
                    pool,
                    codeAttribute.code.toMutableList()
                ),
                variablesNames,
                signature
            )
        }
    }
}