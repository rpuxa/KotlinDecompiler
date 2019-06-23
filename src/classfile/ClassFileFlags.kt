package classfile

enum class ClassFileFlags(val mask: Int) {
    PUBLIC(0x0001),
    FINAL(0x0010),
    SUPER(0x0020),
    INTERFACE(0x0200),
    ABSTRACT(0x0400),
    SYNTETIC(0x1000),
    ANNOTATION(0x2000),
    ENUM(0x4000),
    ;

    companion object {
        fun getFlags(number: Int): Set<ClassFileFlags> {
            val list = HashSet<ClassFileFlags>()
            values().forEach {
                if (number and it.mask == 1)
                    list.add(it)
            }
            return list
        }
    }
}