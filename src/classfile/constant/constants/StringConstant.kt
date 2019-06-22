package classfile.constant.constants

import classfile.ConstantReference
import classfile.constant.Constant
import classfile.constant.ConstantBuilder
import classfile.constant.ConstantPool
import java.io.DataInputStream

class StringConstant(
    private val utfReference: ConstantReference,
    private val pool: ConstantPool
) : Constant() {

    val value: String get() = pool.get<UtfConstant>(utfReference).value

    companion object : ConstantBuilder {
        override val tag get() = 8

        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): StringConstant =
            StringConstant(stream.readUnsignedShort(), pool)
    }
}