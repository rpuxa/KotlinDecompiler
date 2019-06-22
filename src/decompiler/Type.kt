package decompiler

class Type(
    private val path: Array<String>,
    private val name: String
) {


    companion object {
        private val KOTLIN_PACKAGE = arrayOf("kotlin")

        val BYTE = Type(KOTLIN_PACKAGE, "Byte")
        val CHAR = Type(KOTLIN_PACKAGE, "Char")
        val DOUBLE = Type(KOTLIN_PACKAGE, "Double")
        val FLOAT = Type(KOTLIN_PACKAGE, "Float")
        val INT = Type(KOTLIN_PACKAGE, "Int")
        val LONG = Type(KOTLIN_PACKAGE, "Long")
        val SHORT = Type(KOTLIN_PACKAGE, "Short")
        val BOOLEAN = Type(KOTLIN_PACKAGE, "Boolean")

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
                else -> {
                    if (type.startsWith('[')) {
                        TODO("Arrays")
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

        fun fromDescriptor(descriptor: String): Pair<List<Type>, Type> {
            val arguments = descriptor.substring(1, descriptor.lastIndexOf(')'))
            val argumentsList = ArrayList<Type>()
            val iterator = arguments.iterator()
            while (iterator.hasNext()) {
                val char = iterator.next()
                if (char == '[' || char == 'L') {
                    val builder = StringBuilder()
                    builder.append(char)
                    while (true) {
                        val nextChar = iterator.next()
                        if (nextChar == ';')
                            break
                        else
                            builder.append(nextChar)
                    }
                    argumentsList.add(valueOf(builder.toString()))
                } else {
                    argumentsList.add(valueOf(char.toString()))
                }
            }

            return argumentsList to valueOf(descriptor.substring(descriptor.lastIndexOf(')') + 1))
        }
    }
}