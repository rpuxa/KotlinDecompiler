package classfile

enum class ClassFileFlags(val mask: Int) {
    PUBLIC(0x0001)
    ;

    companion object {
        fun getFlags(number: Int): List<ClassFileFlags> {
            val list = ArrayList<ClassFileFlags>()
            values().forEach {
                if (number and it.mask == 1)
                    list.add(it)
            }
            return list
        }
    }
}