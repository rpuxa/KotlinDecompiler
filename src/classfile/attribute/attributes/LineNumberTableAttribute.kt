package classfile.attribute.attributes

import classfile.Readable
import classfile.attribute.Attribute
import classfile.constant.ConstantPool
import java.io.DataInputStream

class LineNumberTableAttribute : Attribute() {
    companion object : Readable<LineNumberTableAttribute> {
        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): LineNumberTableAttribute {
            // skip all
            val length = stream.readUnsignedShort()
            repeat(length) {
                val startPC = stream.readUnsignedShort()
                val lineNumber = stream.readUnsignedShort()
            }

            return LineNumberTableAttribute()
        }
    }
}