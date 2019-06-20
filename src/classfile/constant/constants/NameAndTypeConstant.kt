package classfile.constant.constants

import classfile.ConstantReference
import classfile.constant.Constant
import classfile.constant.ConstantBuilder
import classfile.constant.ConstantPool
import java.io.DataInputStream

class NameAndTypeConstant(
    val nameReference: ConstantReference,
    val descriptorReference: ConstantReference,
    val pool: ConstantPool
) : Constant() {

    val name: UtfConstant get() = pool.get(nameReference)
    val descriptor: UtfConstant get() = pool.get(descriptorReference)

    companion object : ConstantBuilder {
        override val tag get() = 12

        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): NameAndTypeConstant =
            NameAndTypeConstant(
                stream.readUnsignedShort(),
                stream.readUnsignedShort(),
                pool
            )

    }
}