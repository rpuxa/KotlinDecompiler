package classfile.attribute.codeattribute.arguments

import classfile.attribute.codeattribute.InstructionArgument

class ByteArgument private constructor(val value: Int) : InstructionArgument {

    override val bytesCount get() = 1

    companion object {
        @JvmField
        val M_ONE = ByteArgument(-1)
        @JvmField
        val ZERO = ByteArgument(0)
        @JvmField
        val ONE = ByteArgument(1)
        @JvmField
        val TWO = ByteArgument(2)
        @JvmField
        val THREE = ByteArgument(3)
        @JvmField
        val FOUR = ByteArgument(4)
        @JvmField
        val FIVE = ByteArgument(5)

        @JvmName("valueOf")
        operator fun invoke(value: Int) = when (value) {
            -1 -> M_ONE
            0 -> ZERO
            1 -> ONE
            2 -> TWO
            3 -> THREE
            4 -> FOUR
            5 -> FIVE
            else -> ByteArgument(value)
        }
    }

}