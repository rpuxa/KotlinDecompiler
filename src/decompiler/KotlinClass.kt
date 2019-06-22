package decompiler

import classfile.ClassFile

class KotlinClass(
    private val classFile: ClassFile,
    private val functions: List<KotlinFunction>
) {



    companion object {
        fun fromClassFile(classFile: ClassFile): KotlinClass {
            return KotlinClass(classFile, classFile.methods.map { KotlinFunction.fromMethod(it, classFile.constantPool) })
        }
    }
}