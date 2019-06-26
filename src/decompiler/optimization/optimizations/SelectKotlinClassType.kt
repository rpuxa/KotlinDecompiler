package decompiler.optimization.optimizations

import classfile.ClassFileFlags
import classfile.field.FieldFlags
import classfile.method.MethodFlags
import decompiler.ClassReference
import decompiler.KotlinClass
import decompiler.Type
import decompiler.optimization.Optimization

object SelectKotlinClassType : Optimization(false, true) {

    override fun optimize(clazz: KotlinClass) {
        val flags = clazz.classFile.accessFlags
        if (ClassFileFlags.ANNOTATION in flags) {
            clazz.type = KotlinClass.Types.ANNOTATION
            return
        }

        if (ClassFileFlags.INTERFACE in flags) {
            clazz.type = KotlinClass.Types.INTERFACE
            return
        }

        if (ClassFileFlags.ENUM in flags) {
            clazz.type = KotlinClass.Types.ENUM
            return
        }

        if (
            ClassFileFlags.FINAL in flags &&
            clazz.constructors.size == 1 &&
            clazz.constructors.first().run {
                MethodFlags.PRIVATE in method.flags &&
                        method.descriptor.value == "()V"
            } &&
            clazz.classFile.fields.any {
                FieldFlags.PUBLIC in it.flags &&
                        FieldFlags.STATIC in it.flags &&
                        FieldFlags.FINAL in it.flags &&
                        it.name.value == "INSTANCE" &&
                        Type.valueOf(it.descriptor.value) == Type.valueOf(clazz.classFile.thisClass)
            }
        ) {
            clazz.type = KotlinClass.Types.OBJECT
            return
        }

        if (ClassFileFlags.FINAL in flags &&
            clazz.constructors.isEmpty() &&
            clazz.classFile.thisClass.name.value.endsWith("Kt") &&
            clazz.functions.all { MethodFlags.STATIC in it.method.flags } &&
            clazz.properties.all { FieldFlags.STATIC in it.backingField!!.field.flags }
        ) {
            clazz.type = KotlinClass.Types.FILE
            return
        }

        clazz.type = KotlinClass.Types.CLASS
    }
}