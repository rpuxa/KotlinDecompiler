package decompiler

import classfile.constant.ConstantPool
import classfile.method.Method

class KotlinFunction(
    private val method: Method,
    private val code: Block
) : Renderable {

    override fun render(builder: CodeStringBuilder) {
        builder.append("fun")
    }

    companion object {
        fun fromMethod(method: Method, pool: ConstantPool): KotlinFunction {
            return KotlinFunction(
                method,
                BlockBuilder.fromCodeSequence(
                    pool,
                    ControlList(method.attributes.codeAttribute.code.toMutableList())
                )
            )
        }
    }
}