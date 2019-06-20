package classfile.method

import classfile.attribute.Attributes
import classfile.constant.ConstantPool
import classfile.ConstantReference
import classfile.constant.constants.UtfConstant
import java.io.DataInputStream

class Method(
    val flags: List<MethodFlags>,
    val name: UtfConstant,
    val descriptor: UtfConstant,
    val attributes: Attributes
) {

    companion object {
        fun readFromStream(stream: DataInputStream, pool: ConstantPool): Method {
            val flags = MethodFlags.getFlags(stream.readUnsignedShort())
            val name: ConstantReference = stream.readUnsignedShort()
            val descriptor: ConstantReference = stream.readUnsignedShort()
            val attributes = Attributes.readFromStream(stream, pool)

            return Method(flags, pool.get(name), pool.get(descriptor), attributes)
        }
    }
}