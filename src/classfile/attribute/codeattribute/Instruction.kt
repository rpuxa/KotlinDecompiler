package classfile.attribute.codeattribute

import java.io.DataInputStream

class Instruction(val type: InstructionTypes, val argument: InstructionArgument) {

    operator fun component1() = type
    operator fun component2() = argument
    override fun toString(): String {
        return "$type $argument"
    }


    companion object {
        fun readFromStream(stream: DataInputStream): Pair<Instruction, Int> {
            val instructionCode = stream.readUnsignedByte()
            val type = InstructionTypes.valueOf(instructionCode)
            val argument = type.readArgumentFromStream(stream)
            val convertTo = type.convertTo(stream, argument)
            return if (convertTo == null) {
                Instruction(type, argument)
            } else {
                Instruction(convertTo.first, convertTo.second)
            } to 1 + argument.bytesCount
        }
    }
}