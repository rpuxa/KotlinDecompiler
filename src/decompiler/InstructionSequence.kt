package decompiler

import classfile.attribute.codeattribute.Instruction

class InstructionSequence(
    private val list: MutableList<Instruction>,
    private val startPosition: Int = 0,
    private val endPosition: Int = list.lastIndex
) {
    var currentPosition = startPosition
        private set

    fun next() = this[currentPosition++]
    fun previous() = this[currentPosition--]
    fun moveBack(times: Int = 1) {
        currentPosition -= times
    }

    operator fun get(index: Int): Instruction? {
        if (index < startPosition || index > endPosition)
            return null
        return list[index]
    }

    fun jumpTo(position: Int) {
        currentPosition = position
    }

    fun reset() = jumpTo(0)

    fun subSequence(from: Int, to: Int) = InstructionSequence(list, from, to)

    fun replace(instruction: Instruction) {
        list[currentPosition] = instruction
    }

    fun indexOf(instruction: Instruction) = list.indexOf(instruction)
    fun current() = list[currentPosition]
    fun moveForward(times: Int = 1) {
        currentPosition += times
    }

    fun lastElement(): Boolean = currentPosition >= endPosition
}