package classfile.attribute

import classfile.constant.ConstantPool
import classfile.Readable
import java.io.DataInputStream
import kotlin.test.fail

abstract class Attribute {

    companion object : Readable<Attribute?> {

        private val attributesToSkip = arrayOf(
            "StackMapTable",
            "RuntimeInvisibleParameterAnnotations",
            "SourceDebugExtension",
            "RuntimeVisibleAnnotations"
        )

        override fun readFromStream(stream: DataInputStream, pool: ConstantPool): Attribute? {
            val attrName = pool.getName(stream.readUnsignedShort())
            val length = stream.readInt()
            if (attrName in attributesToSkip) {
                stream.skipBytes(length)
                return null
            }
            try {
                val readable: Readable<Attribute> =
                    Class.forName("classfile.attribute.attributes.${attrName}Attribute")
                        .getField("Companion")
                        .get(null) as Readable<Attribute>
                return readable.readFromStream(stream, pool)
            } catch (e: ClassNotFoundException) {
                fail("Unknown attribute: ${e.message}")
            }
        }
    }
}