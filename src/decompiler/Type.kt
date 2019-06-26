@file:Suppress("EqualsOrHashCode")

package decompiler

import allSame
import classfile.constant.constants.ClassConstant
import kotlin.collections.HashSet
import kotlin.test.fail

class Type : Renderable {

    lateinit var reference: ClassReference
        private set
    val name: String

    private val isPrimitive: Boolean
    val nullable: Boolean

    private constructor(name: String, nullable: Boolean) {
        this.name = name
        isPrimitive = true
        this.nullable = nullable
    }

    constructor(reference: ClassReference, nullable: Boolean) {
        this.reference = reference
        isPrimitive = false
        name = reference.name
        this.nullable = nullable
    }


    override fun render(builder: CodeStringBuilder) {
        builder.append(name)
        if (nullable)
            builder.append('?')
    }


    fun toNullable(): Type {
        if (nullable)
            return this
        if (!isPrimitive) {
            return Type(reference, true)
        }
        return when (name) {
            BYTE_NAME -> NULLABLE_BYTE
            CHAR_NAME -> NULLABLE_CHAR
            DOUBLE_NAME -> NULLABLE_DOUBLE
            FLOAT_NAME -> NULLABLE_FLOAT
            INT_NAME -> NULLABLE_INT
            LONG_NAME -> NULLABLE_LONG
            SHORT_NAME -> NULLABLE_SHORT
            BOOLEAN_NAME -> NULLABLE_BOOLEAN
            else -> fail()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Type) return false

        if (isPrimitive) return false

        return reference == other.reference
    }


    companion object {

        private const val BYTE_DESCRIPTOR = "B"
        private const val CHAR_DESCRIPTOR = "C"
        private const val DOUBLE_DESCRIPTOR = "D"
        private const val FLOAT_DESCRIPTOR = "F"
        private const val INT_DESCRIPTOR = "I"
        private const val LONG_DESCRIPTOR = "J"
        private const val SHORT_DESCRIPTOR = "S"
        private const val BOOLEAN_DESCRIPTOR = "Z"
        private const val UNIT_DESCRIPTOR = "V"

        private const val NO_TYPE_NAME = "<NO_TYPE>"
        private const val UNIT_NAME = "Unit"
        private const val NOTHING_NAME = "Nothing"
        private const val BYTE_NAME = "Byte"
        private const val CHAR_NAME = "Char"
        private const val DOUBLE_NAME = "Double"
        private const val FLOAT_NAME = "Float"
        private const val INT_NAME = "Int"
        private const val LONG_NAME = "Long"
        private const val SHORT_NAME = "Short"
        private const val BOOLEAN_NAME = "Boolean"

        private const val STRING_CLASS_NAME = "Ljava/lang/String;"
        private const val NOTHING_CLASS_NAME = "Ljava/lang/Void;"
        private const val BYTE_CLASS_NAME = "Ljava/lang/Byte;"
        private const val CHAR_CLASS_NAME = "Ljava/lang/Character;"
        private const val DOUBLE_CLASS_NAME = "Ljava/lang/Double;"
        private const val FLOAT_CLASS_NAME = "Ljava/lang/Float;"
        private const val INTEGER_CLASS_NAME = "Ljava/lang/Integer;"
        private const val LONG_CLASS_NAME = "Ljava/lang/Long;"
        private const val SHORT_CLASS_NAME = "Ljava/lang/Short;"
        private const val BOOLEAN_CLASS_NAME = "Ljava/lang/Boolean;"

        private val STRING_CLASS_REFERENCE = ClassReference.valueOf(STRING_CLASS_NAME)

        val STRING = Type(STRING_CLASS_REFERENCE, false)
        val NULLABLE_STRING = Type(STRING_CLASS_REFERENCE, true)

        val NO_TYPE = Type(NO_TYPE_NAME, false)

        val UNIT = Type(UNIT_NAME, false)

        val NOTHING = Type(NOTHING_NAME, false)
        val NULLABLE_NOTHING = Type(NOTHING_NAME, true)

        val BYTE = Type(BYTE_NAME, false)
        val NULLABLE_BYTE = Type(BYTE_NAME, true)

        val CHAR = Type(CHAR_NAME, false)
        val NULLABLE_CHAR = Type(CHAR_NAME, true)

        val DOUBLE = Type(DOUBLE_NAME, false)
        val NULLABLE_DOUBLE = Type(DOUBLE_NAME, true)

        val FLOAT = Type(FLOAT_NAME, false)
        val NULLABLE_FLOAT = Type(FLOAT_NAME, true)

        val INT = Type(INT_NAME, false)
        val NULLABLE_INT = Type(INT_NAME, true)

        val LONG = Type(LONG_NAME, false)
        val NULLABLE_LONG = Type(LONG_NAME, true)

        val SHORT = Type(SHORT_NAME, false)
        val NULLABLE_SHORT = Type(SHORT_NAME, true)

        val BOOLEAN = Type(BOOLEAN_NAME, false)
        val NULLABLE_BOOLEAN = Type(BOOLEAN_NAME, true)


        fun valueOf(type: String, nullable: Boolean = false): Type {
            return when (type) {
                BYTE_DESCRIPTOR -> BYTE
                CHAR_DESCRIPTOR -> CHAR
                DOUBLE_DESCRIPTOR -> DOUBLE
                FLOAT_DESCRIPTOR -> FLOAT
                INT_DESCRIPTOR -> INT
                LONG_DESCRIPTOR -> LONG
                SHORT_DESCRIPTOR -> SHORT
                BOOLEAN_DESCRIPTOR -> BOOLEAN
                UNIT_DESCRIPTOR -> UNIT
                NOTHING_CLASS_NAME -> if (nullable) NULLABLE_NOTHING else NOTHING
                BYTE_CLASS_NAME -> NULLABLE_BYTE
                CHAR_CLASS_NAME -> NULLABLE_CHAR
                DOUBLE_CLASS_NAME -> NULLABLE_DOUBLE
                FLOAT_CLASS_NAME -> NULLABLE_FLOAT
                INTEGER_CLASS_NAME -> NULLABLE_INT
                LONG_CLASS_NAME -> NULLABLE_LONG
                SHORT_CLASS_NAME -> NULLABLE_SHORT
                BOOLEAN_CLASS_NAME -> NULLABLE_BOOLEAN
                STRING_CLASS_NAME -> if (nullable) NULLABLE_STRING else STRING
                else -> {
                    if (type.startsWith('[')) {
                        TODO("ARRAYS")
                    } else {
                        Type(ClassReference.valueOf(type), false)
                    }
                }
            }
        }

        fun valueOf(constant: ClassConstant, nullable: Boolean = false): Type {
            return Type(ClassReference.valueOf(constant), nullable)
        }

        fun mergeTypes(types: List<Type>, classInformer: ClassInformer = ReflectionClassInformer): Type {
            val nullable = types.any { it.nullable }
            if (types.allSame()) {
                return types.first().toNullable()
            }
            val filtered = types.filter { it != NOTHING }

            if (filtered.size == 1)
                return filtered.first().toNullable()

            val commonParents = HashSet(classInformer.findAllParents(filtered.first().reference))
            filtered.forEachIndexed { index, type ->
                if (index == 0)
                    return@forEachIndexed

                commonParents.retainAll(classInformer.findAllParents(type.reference))
            }

            val isNotNearestParents = HashSet<ClassReference>()
            commonParents.forEach {
                isNotNearestParents.addAll(classInformer.findClassParents(it))
            }


            val reference = commonParents.first { it !in isNotNearestParents }
            return Type(reference, nullable)
        }
    }


}




































