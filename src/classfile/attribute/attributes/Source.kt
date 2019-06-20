package classfile.attribute.attributes

import classfile.Readable
import classfile.attribute.Attribute
import classfile.constant.ConstantPool
import java.io.DataInputStream

class SourceFileAttribute : Attribute() {
    companion object : Readable<SourceFileAttribute> {
        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): SourceFileAttribute {

            // Skip all

            val sourceFile = stream.readUnsignedShort()

            return SourceFileAttribute()
        }
    }
}