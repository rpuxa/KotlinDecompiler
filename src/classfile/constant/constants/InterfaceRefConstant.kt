/*
package classfile.constant.constants

import classfile.constant.Constant
import classfile.constant.ConstantBuilder
import classfile.constant.ConstantPool
import java.io.DataInputStream


class InterfaceRefConstant(val clazz: ClassConstant, val name: NameAndTypeConstant) : Constant() {
    companion object : ConstantBuilder {

        override val tag get() = 11

        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): InterfaceRefConstant =
            InterfaceRefConstant(
                pool.get(stream.readUnsignedShort()),
                pool.get(stream.readUnsignedShort())
            )
    }
}*/
