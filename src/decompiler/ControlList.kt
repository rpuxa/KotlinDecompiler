package decompiler

import classfile.attribute.codeattribute.Instruction

class ControlList<T>(
    private val list: MutableList<T>,
    private val startPosition: Int = 0,
    private val endPosition: Int = list.lastIndex
) {
    var currentPosition = startPosition
        private set

    fun currentAndMoveForward() = this[currentPosition++]!!
    fun currentOrNullAndMoveForward() = this[currentPosition++]
    fun next() = this[++currentPosition]

    fun previous() = this[currentPosition--]
    fun moveBack(times: Int = 1) {
        currentPosition -= times
    }

    operator fun get(index: Int): T? {
        if (index < startPosition || index > endPosition)
            return null
        return list[index]
    }

    fun jumpTo(position: Int) {
        currentPosition = position
    }

    fun reset() = jumpTo(startPosition)

    fun subSequence(from: Int, to: Int) = ControlList(list, from, to)

    fun replace(t: T) {
        list[currentPosition] = t
    }

    fun indexOf(t: T) = list.indexOf(t)
    fun current() = list[currentPosition]
    fun currentOrNull() = if (outBound()) null else list[currentPosition]
    fun moveForward(times: Int = 1) {
        currentPosition += times
    }

    fun lastElement(): Boolean = currentPosition >= endPosition
    fun outBound(): Boolean = currentPosition > endPosition

    inline fun forEach(block: (T) -> Unit) {
        while (!outBound()) {
            block(current())
            moveForward()
        }
    }
}