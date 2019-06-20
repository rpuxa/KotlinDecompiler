package classfile.constant.constants

import classfile.constant.Constant
import classfile.constant.ConstantBuilder
import classfile.constant.ConstantPool
import java.io.DataInputStream

class UtfConstant(val value: String) : Constant() {
    companion object : ConstantBuilder {
        override val tag get() = 1

        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): UtfConstant =
            UtfConstant(stream.readUTF())
    }
}