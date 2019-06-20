package classfile.constant.constants

import classfile.ConstantReference
import classfile.constant.Constant
import classfile.constant.ConstantBuilder
import classfile.constant.ConstantPool
import java.io.DataInputStream

class ClassConstant(val nameReference: ConstantReference, val pool: ConstantPool) : Constant() {

    val name: UtfConstant  get() = pool.get(nameReference)

    companion object : ConstantBuilder {
        override val tag get() = 7

        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): ClassConstant =
            ClassConstant(stream.readUnsignedShort(), pool)
    }
}