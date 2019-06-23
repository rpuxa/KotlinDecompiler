package decompiler

import classfile.constant.constants.FieldRefConstant
import classfile.constant.constants.MethodRefConstant

class MemberReference(
    val clazz: ClassReference,
    val name: String,
    val signature: Signature
) {

    companion object {
        fun valueOf(methodRefConstant: MethodRefConstant): MemberReference {
            val clazz = ClassReference.valueOf(methodRefConstant.clazz)
            val name = methodRefConstant.name.name.value
            val signature = Signature.fromDescriptor(methodRefConstant.name.descriptor.value)

            return MemberReference(
                clazz,
                name,
                signature
            )
        }

        fun valueOf(fieldRefConstant: FieldRefConstant): MemberReference {
            val clazz = ClassReference.valueOf(fieldRefConstant.clazz)
            val name = fieldRefConstant.name.name.value
            val signature = Signature.fromDescriptor(fieldRefConstant.name.descriptor.value)

            return MemberReference(
                clazz,
                name,
                signature
            )
        }
    }
}