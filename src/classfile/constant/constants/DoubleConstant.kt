package classfile.constant.constants

import classfile.constant.Constant
import classfile.constant.ConstantBuilder
import classfile.constant.ConstantPool
import java.io.DataInputStream

class DoubleConstant(val value: Double) : Constant() {
    companion object : ConstantBuilder {
        override val tag get() = 6

        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): DoubleConstant =
            DoubleConstant(stream.readDouble())
    }
}