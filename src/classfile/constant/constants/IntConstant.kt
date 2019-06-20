package classfile.constant.constants

import classfile.constant.Constant
import classfile.constant.ConstantBuilder
import classfile.constant.ConstantPool
import java.io.DataInputStream

class IntConstant(val value: Int) : Constant() {
    companion object : ConstantBuilder {
        override val tag get() = 3

        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): IntConstant =
            IntConstant(stream.readInt())
    }
}