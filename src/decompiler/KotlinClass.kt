package decompiler

import classfile.ClassFile
import decompiler.optimization.Optimizations

class KotlinClass(
    var classFile: ClassFile,
    val fields: MutableList<KotlinField>,
    val properties: MutableList<KotlinProperty>,
    val functions: MutableList<KotlinFunction>
) : Renderable {

    val constructors: MutableList<Constructor> = ArrayList()

    val name = classFile.thisClass.name.value.split('/').last()

    lateinit var type: Types

    override fun render(builder: CodeStringBuilder) {
        val headline = when (type) {
            Types.CLASS -> "class"
            Types.INTERFACE -> "interface"
            Types.ENUM -> "enum class"
            Types.ANNOTATION -> "annotation class"
            Types.OBJECT -> "object"
            else -> ""
        }
        val isNotFile = type != Types.FILE
        if (isNotFile) {
            builder.append(headline)
                .append(' ')
                .append(classFile.thisClass.name.value)
                .append(" {")
                .newLine()
                .addTab()
        }

        properties.forEach {
            builder.newLine()
                .append(it)
        }

        constructors.forEach {
            builder.newLine()
                .append(it)
        }

        functions.forEach {
            builder.newLine()
                .append(it)
        }

        if (isNotFile) {
            builder.removeTab()
            builder.append('}')
        }
    }

    enum class Types {
        FILE,
        CLASS,
        INTERFACE,
        ENUM,
        OBJECT,
        ANNOTATION,
    }

    companion object {
        fun fromClassFile(classFile: ClassFile): KotlinClass {
            return KotlinClass(
                classFile,
                classFile.fields.map { KotlinField(it) }.toMutableList(),
                mutableListOf(),
                classFile.methods.map { KotlinFunction.fromMethod(it, classFile.constantPool) }.toMutableList()
            ).apply {
                Optimizations.optimize(this)
            }
        }
    }
}