import java.io.DataInputStream

fun DataInputStream.readUnsignedInt(): Long = readInt().toLong() and 0xFFFF_FFFF

inline fun repeat(times: Long, block: (Long) -> Unit) {
    for (i in 0 until times) {
        block(i)
    }
}

fun <T> Iterable<T>.hasSingleElement(): Boolean {
    val iterator = iterator()
    if (!iterator.hasNext())
        return false
    iterator.next()
    return !iterator.hasNext()
}
fun <T> MutableList<T>.pop(): T {
    return removeAt(size - 1)
}