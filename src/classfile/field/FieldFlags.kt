package classfile.field

enum class FieldFlags(val mask: Int) {
    PUBLIC(0x0001)
    ;

    companion object {
        fun getFlags(number: Int): List<FieldFlags> {
            val list = ArrayList<FieldFlags>()
            values().forEach {
                if (number and it.mask == 1)
                    list.add(it)
            }
            return list
        }
    }
}