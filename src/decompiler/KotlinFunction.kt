package decompiler

import classfile.constant.ConstantPool
import classfile.method.Method
import classfile.method.MethodFlags
import decompiler.elements.Block

class KotlinFunction(
    val method: Method,
    val code: Block,
    val variablesNames: VariablesNames
) : Renderable {

    val signature = Signature.fromDescriptor(method.descriptor.value)


    override fun render(builder: CodeStringBuilder) {
        builder.append("fun ")
            .append(method.name.value)
            .append('(')
        signature.arguments.forEachIndexed { index, argument ->
            if (index != 0) {
                builder.append(", ")
            }
            builder.append(variablesNames[index])
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
            val isStatic = MethodFlags.STATIC in method.flags
            val variablesNames = VariablesNames(method.attributes.codeAttribute.maxLocals + if (isStatic) 0 else 1)
            if (!isStatic) {
                variablesNames[0] = "this"
            }
            return KotlinFunction(
                method,
                BlockBuilder.fromCodeSequence(
                    pool,
                    method.attributes.codeAttribute.code.toMutableList(),
                    variablesNames
                ),
                variablesNames
            )
        }
    }
}