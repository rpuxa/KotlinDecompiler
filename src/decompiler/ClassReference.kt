package decompiler

import classfile.constant.constants.ClassConstant
import java.lang.StringBuilder

class ClassReference(
    val path: Array<String>,
    val name: String
) {




    companion object {
        fun valueOf(classConstant: ClassConstant): ClassReference {
            val split = classConstant.name.value.split('/')
            return ClassReference(
                Array(split.size - 1) { split[it] },
                split.last()
            )
        }

        fun valueOf(descriptor: String): ClassReference? {
            if (descriptor[0] != 'L')
                return null
            val split = descriptor.substring(1).split('/')
            return ClassReference(
                Array(split.size - 1) { split[it] },
                split.last()
            )
        }
    }

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
}