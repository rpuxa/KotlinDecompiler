package classfile.constant.constants

import classfile.constant.Constant
import classfile.constant.ConstantBuilder
import classfile.constant.ConstantPool
import java.io.DataInputStream

class FloatConstant(val value: Float) : Constant() {
    companion object : ConstantBuilder {
        override val tag get() = 4

        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): FloatConstant =
            FloatConstant(stream.readFloat())
    }
}