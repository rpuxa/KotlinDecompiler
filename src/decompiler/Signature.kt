package decompiler

class Signature(
    val arguments: Array<Type>,
    val returnType: Type
) {


    val arity = arguments.size

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Signature) return false

        return arguments.contentEquals(other.arguments) && returnType != other.returnType
    }

    override fun hashCode(): Int {
        var result = arguments.hashCode()
        result = 31 * result + returnType.hashCode()
        return result
    }

    companion object {
        fun fromDescriptor(descriptor: String): Signature {
            val bracket = descriptor.lastIndexOf(')')
            if (bracket == -1) {
                return Signature(emptyArray(), Type.valueOf(descriptor))
            }
            val arguments = descriptor.substring(1, bracket)
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
                    argumentsList.add(Type.valueOf(builder.toString()))
                } else {
                    argumentsList.add(Type.valueOf(char.toString()))
                }
            }

            return Signature(
                argumentsList.toTypedArray(),
                Type.valueOf(
                    descriptor.substring(bracket + 1)
                )
            )
        }
    }
}