package classfile.method


enum class MethodFlags(val mask: Int) {
    PUBLIC(0x0001)
    ;

    companion object {
        fun getFlags(number: Int): List<MethodFlags> {
            val list = ArrayList<MethodFlags>()
            values().forEach {
                if (number and it.mask == 1)
                    list.add(it)
            }
            return list
        }
    }


}