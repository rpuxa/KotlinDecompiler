package classfile.constant

enum class ConstantType(val value: Int) {
    Class(7)


    ;

    companion object {
        fun valueOf(value: Int) = values().first { it.value == value }
    }
}