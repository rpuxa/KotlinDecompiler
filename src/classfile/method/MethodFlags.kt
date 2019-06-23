package classfile.method


enum class MethodFlags(val mask: Int) {
    PUBLIC(0x0001),
    PRIVATE(0x0002),
    PROTECTED(0x0004),
    STATIC(0x0008),
    FINAL(0x0010),
    SYNCRONIZED(0x0020),
    BRIDGE(0x0040),
    VARARGS(0x0080),
    NATIVE(0x0100),
    ABSTRACT(0x0400),
    STRICT(0x0800),
    SYNTETIC(0x1000)
    ;

    companion object {
        fun getFlags(number: Int): Set<MethodFlags> {
            val list = HashSet<MethodFlags>()
            values().forEach {
                if (number and it.mask == 1)
                    list.add(it)
            }
            return list
        }
    }


}