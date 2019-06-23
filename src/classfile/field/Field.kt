package classfile.field

import classfile.attribute.Attributes
import classfile.constant.ConstantPool
import classfile.ConstantReference
import classfile.constant.constants.UtfConstant
import java.io.DataInputStream

class Field(
    val flags: Set<FieldFlags>,
    val name: UtfConstant,
    val descriptor: UtfConstant,
    val attributes: Attributes
) {

    companion object {
        fun readFromStream(stream: DataInputStream, pool: ConstantPool): Field {
            val flags = FieldFlags.getFlags(stream.readUnsignedShort())
            val name: ConstantReference = stream.readUnsignedShort()
            val descriptor: ConstantReference = stream.readUnsignedShort()
            val attributes = Attributes.readFromStream(stream, pool)

            return Field(flags, pool.get(name), pool.get(descriptor), attributes)
        }
    }
}