package decompiler

import classfile.constant.constants.FieldRefConstant
import classfile.constant.constants.MethodRefConstant

class MemberReference(
    val clazz: ClassReference,
    val name: String,
    val arguments: List<Type>,
    val returnType: Type
) {

    companion object {
        fun valueOf(methodRefConstant: MethodRefConstant): MemberReference {
            val clazz = ClassReference.valueOf(methodRefConstant.clazz)
            val name = methodRefConstant.name.name.value
            val (arguments, returnType) = Type.fromDescriptor(methodRefConstant.name.descriptor.value)

            return MemberReference(
                clazz,
                name,
                arguments,
                returnType
            )
        }

        fun valueOf(fieldRefConstant: FieldRefConstant): MemberReference {
            val clazz = ClassReference.valueOf(fieldRefConstant.clazz)
            val name = fieldRefConstant.name.name.value
            val returnType = Type.valueOf(fieldRefConstant.name.descriptor.value)

            return MemberReference(
                clazz,
                name,
                emptyList(),
                returnType
            )
        }
    }
}