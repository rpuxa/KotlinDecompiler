package classfile.field

enum class FieldFlags(val mask: Int) {
    PUBLIC(0x0001),
    PRIVATE(0x0002),
    PROTECTED(0x0004),
    STATIC(0x0008),
    FINAL(0x0010),
    VOLATILE(0x0040),
    TRANSIENT(0x0080),
    SYNTETIC(0x1000),
    ENUM(0x4000),
    ;

    companion object {
        fun getFlags(number: Int): Set<FieldFlags> {
            val list = HashSet<FieldFlags>()
            values().forEach {
                if (number and it.mask == 1)
                    list.add(it)
            }
            return list
        }
    }
}