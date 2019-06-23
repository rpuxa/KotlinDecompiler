package classfile.attribute

import classfile.attribute.attributes.CodeAttribute
import classfile.attribute.attributes.ConstantValueAttribute
import classfile.constant.ConstantPool
import java.io.DataInputStream

open class Attributes(val list: List<Attribute>) {

    val codeAttribute get() = getAttribute<CodeAttribute>()!!

    inline fun <reified T> getAttribute() = list.find { it is T } as T?


    companion object {
        fun readFromStream(stream: DataInputStream, pool: ConstantPool): Attributes {
            val count = stream.readUnsignedShort()
            val list = ArrayList<Attribute>()
            repeat(count) {
                Attribute.readFromStream(stream, pool)?.let(list::add)
            }

            return Attributes(list)
        }
    }
}