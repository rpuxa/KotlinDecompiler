package classfile.attribute.codeattribute

import java.io.DataInputStream
import kotlin.jvm.internal.Ref

class Instruction(
    val type: InstructionTypes,
    val argument: InstructionArgument,
    val byteNumber: Int = -1
) {

    operator fun component1() = type
    operator fun component2() = argument
    override fun toString(): String {
        return "$type $argument"
    }


    companion object {
        fun readFromStream(stream: DataInputStream, currentByte: Int): Pair<Instruction, Int> {
            val instructionCode = stream.readUnsignedByte()
            val type = InstructionTypes.valueOf(instructionCode)
            val argument = type.readArgumentFromStream(stream)
            val convertTo = type.convertTo(stream, argument)
            return if (convertTo == null) {
                Instruction(type, argument, currentByte)
            } else {
                Instruction(convertTo.first, convertTo.second, currentByte)
            } to 1 + argument.bytesCount
        }
    }
}