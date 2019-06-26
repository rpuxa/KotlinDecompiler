package classfile.attribute.attributes

import classfile.Readable
import classfile.attribute.Attribute
import classfile.constant.ConstantPool
import classfile.constant.constants.UtfConstant
import java.io.DataInputStream

class LocalVariableTableAttribute(val variables: Array<VariableInfo>) : Attribute() {


    class VariableInfo(
        val start: Int,
        val end: Int,
        val name: UtfConstant,
        val descriptor: UtfConstant,
        val variableIndex: Int
    ) {
        operator fun component1() = name
        operator fun component2() = descriptor
    }



    fun getName(variableIndex: Int, instructionByteNumber: Int) =
        variables.find { it.variableIndex == variableIndex && instructionByteNumber in it.start until it.end }!!.name.value

    fun getArgumentName(index: Int) =
        variables.find { it.variableIndex == index }!!.name.value


    companion object : Readable<LocalVariableTableAttribute> {

        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): LocalVariableTableAttribute {
            val tableLength = stream.readUnsignedShort()
            val variables = Array(tableLength) {
                val startPC = stream.readUnsignedShort()
                val length = stream.readUnsignedShort()
                val name = stream.readUnsignedShort()
                val descriptor = stream.readUnsignedShort()
                val index = stream.readUnsignedShort()

                VariableInfo(startPC, startPC + length, pool.get(name), pool.get(descriptor), index)
            }

            return LocalVariableTableAttribute(variables)
        }

    }
}