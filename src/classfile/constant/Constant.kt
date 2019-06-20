package classfile.constant

import classfile.Readable
import classfile.constant.constants.*
import java.io.DataInputStream
import kotlin.test.fail

abstract class Constant {

    companion object : Readable<Constant> {
        private val allConstants = arrayOf(
            ClassConstant,
            DoubleConstant,
            FieldRefConstant,
            FloatConstant,
            IntConstant,
           // InterfaceRefConstant,
            LongConstant,
            MethodRefConstant,
            NameAndTypeConstant,
            StringConstant,
            UtfConstant
        )

        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): Constant {
            val tag = stream.readUnsignedByte()
            return allConstants.find { it.tag == tag }?.readFromStream(stream, pool) ?: fail("Unknown constant tag: $tag")
        }
    }
}