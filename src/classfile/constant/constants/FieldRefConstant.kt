package classfile.constant.constants

import classfile.ConstantReference
import classfile.constant.Constant
import classfile.constant.ConstantBuilder
import classfile.constant.ConstantPool
import java.io.DataInputStream

class FieldRefConstant(
    val classReference: ConstantReference,
    val nameReference: ConstantReference,
    private val pool: ConstantPool
) : Constant() {

    val clazz: ClassConstant get() = pool.get(classReference)
    val name: NameAndTypeConstant get() = pool.get(nameReference)

    companion object : ConstantBuilder {

        override val tag get() = 9

        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): FieldRefConstant =
            FieldRefConstant(
                stream.readUnsignedShort(),
                stream.readUnsignedShort(),
                pool
            )
    }
}