package decompiler

import classfile.constant.constants.ClassConstant
import java.lang.StringBuilder

class ClassReference(
    val path: Array<String>,
    val name: String
) : Renderable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ClassReference) return false
        if (!path.contentEquals(other.path)) return false
        if (name != other.name) return false
        return true
    }

    override fun hashCode(): Int {
        var result = path.contentHashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun render(builder: CodeStringBuilder) {
        builder.append(name)
    }

    companion object {
        val OBJECT = ClassReference(arrayOf("java", "lang"), "Object")

        fun valueOf(classConstant: ClassConstant): ClassReference {
            val split = classConstant.name.value.split('/')
            return ClassReference(
                Array(split.size - 1) { split[it] },
                split.last()
            )
        }

        fun valueOf(descriptor: String): ClassReference {
            val split = descriptor.substring(1).split('/')
            val last = split.last()
            return ClassReference(
                Array(split.size - 1) { split[it] },
                last.substring(0, last.length - 1)
            )
        }

        fun valueOf(clazz: Class<*>): ClassReference {
            return ClassReference(
                clazz.packageName.split('.').toTypedArray(),
                clazz.simpleName
            )
        }
    }
}