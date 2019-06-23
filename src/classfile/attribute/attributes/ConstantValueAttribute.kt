package classfile.attribute.attributes

import classfile.ConstantReference
import classfile.Readable
import classfile.attribute.Attribute
import classfile.constant.Constant
import classfile.constant.ConstantPool
import java.io.DataInputStream

class ConstantValueAttribute(val value: Constant) : Attribute() {

    companion object : Readable<ConstantValueAttribute> {
        override fun readFromStream(stream: DataInputStream, pool: ConstantPool) =
            ConstantValueAttribute(pool.getConstant(stream.readUnsignedShort()))
    }
}