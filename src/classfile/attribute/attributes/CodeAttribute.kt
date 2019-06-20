package classfile.attribute.attributes

import classfile.Readable
import classfile.attribute.Attribute
import classfile.attribute.Attributes
import classfile.attribute.codeattribute.Instruction
import classfile.attribute.codeattribute.InstructionTypes
import classfile.attribute.codeattribute.arguments.Jump
import classfile.attribute.codeattribute.arguments.ShortArgument
import classfile.constant.ConstantPool
import java.io.DataInputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CodeAttribute(
    val maxStack: Int,
    val maxLocals: Int,
    val code: List<Instruction>,
    val attributes: Attributes
) : Attribute() {

    companion object : Readable<CodeAttribute> {
        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): CodeAttribute {
            val maxStack = stream.readUnsignedShort()
            val maxLocals = stream.readUnsignedShort()
            val codeLength = stream.readInt()
            val bytesToInstruction = HashMap<Int, Instruction>()
            var currentByte = 0
            val instructions = LinkedList<Instruction>()
            while (currentByte < codeLength) {
                val (instruction,   bytes) = Instruction.readFromStream(stream)
                bytesToInstruction[currentByte] = instruction
                currentByte += bytes
                instructions.add(instruction)
            }

            val labels = HashMap<Instruction, Instruction>()

            bytesToInstruction.entries.forEach { (byte, instruction) ->
                val arg = instruction.argument
                if (arg is Jump) {
                    val i = bytesToInstruction[byte + arg.offset]!!
                    val label = labels[i] ?: Instruction(InstructionTypes.LABEL, ShortArgument(labels.size))
                    labels[i] = label
                    arg.labelId = (label.argument as ShortArgument).value
                }
            }

            val code = ArrayList<Instruction>()

            instructions.forEach {
                val label = labels[it]
                if (label != null) {
                    code.add(label)
                }
                code.add(it)
            }

            val exceptionTableLength = stream.readUnsignedShort()
            //TODO exception table
            val attributes = Attributes.readFromStream(stream, pool)


            return CodeAttribute(
                maxStack,
                maxLocals,
                code,
                attributes
            )
        }
    }
}