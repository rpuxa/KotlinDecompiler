package decompiler

 class Type(
    val path: Array<String>,
    val name: String
) {




     override fun equals(other: Any?): Boolean {
         if (this === other) return true
         if (other !is Type) return false

         if (!path.contentEquals(other.path)) return false
         if (name != other.name) return false

         return true
     }

     override fun hashCode(): Int {
         var result = path.contentHashCode()
         result = 31 * result + name.hashCode()
         return result
     }

     companion object {
         private val KOTLIN_PACKAGE = arrayOf("kotlin")

         val UNIT = Type(KOTLIN_PACKAGE, "Unit")
         val BYTE = Type(KOTLIN_PACKAGE, "Byte")
         val CHAR = Type(KOTLIN_PACKAGE, "Char")
         val DOUBLE = Type(KOTLIN_PACKAGE, "Double")
         val FLOAT = Type(KOTLIN_PACKAGE, "Float")
         val INT = Type(KOTLIN_PACKAGE, "Int")
         val LONG = Type(KOTLIN_PACKAGE, "Long")
         val SHORT = Type(KOTLIN_PACKAGE, "Short")
         val BOOLEAN = Type(KOTLIN_PACKAGE, "Boolean")
         val ARRAY = Type(KOTLIN_PACKAGE, "Array")

         fun valueOf(type: String): Type {
             return when (type) {
                 "B" -> BYTE
                 "C" -> CHAR
                 "D" -> DOUBLE
                 "F" -> FLOAT
                 "I" -> INT
                 "J" -> LONG
                 "S" -> SHORT
                 "Z" -> BOOLEAN
                 "V" -> UNIT
                 else -> {
                     if (type.startsWith('[')) {
                         ARRAY // TODO arrays
                     } else {
                         val split = type.substring(1).split('/')
                         var last = split.last()
                         if (last.endsWith(';')) {
                             last = last.substring(0, last.length - 1)
                         }
                         Type(
                             Array(split.size - 1) { split[it] },
                             last
                         )
                     }
                 }
             }
         }
     }
 }