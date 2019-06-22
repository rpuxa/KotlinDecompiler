package decompiler

import classfile.constant.constants.ClassConstant

class ClassReference(
    val path: Array<String>,
    val name: String
) {

    companion object {
        fun valueOf(classConstant: ClassConstant): ClassReference {
            val split = classConstant.name.value.split('/')
            return ClassReference(
                Array(split.size) { split[it] },
                split.last()
            )
        }
    }
}