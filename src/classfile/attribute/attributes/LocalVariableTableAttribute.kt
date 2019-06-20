package classfile.attribute.attributes

import classfile.Readable
import classfile.attribute.Attribute
import classfile.constant.ConstantPool
import java.io.DataInputStream

class LocalVariableTableAttribute : Attribute() {
    companion object : Readable<LocalVariableTableAttribute> {
        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): LocalVariableTableAttribute {

            // Skip all

            val tableLength = stream.readUnsignedShort()
            repeat(tableLength) {
                val startPC  = stream.readUnsignedShort()
                val length  = stream.readUnsignedShort()
                val name = stream.readUnsignedShort()
                val descriptor = stream.readUnsignedShort()
                val index = stream.readUnsignedShort()
            }

            return LocalVariableTableAttribute()
        }
    }
}