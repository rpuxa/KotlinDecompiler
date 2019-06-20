package classfile.constant.constants

import classfile.constant.Constant
import classfile.constant.ConstantBuilder
import classfile.constant.ConstantPool
import java.io.DataInputStream

class LongConstant(val value: Long) : Constant() {
    companion object : ConstantBuilder {
        override val tag get() = 5

        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): LongConstant =
            LongConstant(stream.readLong())
    }
}