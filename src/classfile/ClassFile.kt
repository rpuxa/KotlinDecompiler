package classfile

import classfile.attribute.Attributes
import classfile.constant.constants.ClassConstant
import classfile.constant.ConstantPool
import classfile.field.Field
import classfile.method.Method
import java.io.DataInputStream
import kotlin.test.fail

typealias ConstantReference = Int

class ClassFile(
    val minorVersion: Int,
    val majorVersion: Int,
    val constantPool: ConstantPool,
    val accessFlags: Set<ClassFileFlags>,
    val thisClass: ClassConstant,
    val superClass: ClassConstant,
    val interfaces: List<ConstantReference>,
    val fields: List<Field>,
    val methods: List<Method>,
    val attributes: Attributes
) {


    companion object {
        private const val MAGIC_CONSTANT = 0xCAFEBABE.toInt()

        fun readFromStream(stream: DataInputStream): ClassFile {
            val magic = stream.readInt()
            if (magic != MAGIC_CONSTANT)
                fail("File is not class file")
            val minorVersion = stream.readUnsignedShort()
            val majorVersion = stream.readUnsignedShort()
            val constantPool = ConstantPool.readFromStream(stream)
            val accessFlags = ClassFileFlags.getFlags(stream.readUnsignedShort())
            val thisClass: ConstantReference = stream.readUnsignedShort()
            val superClass: ConstantReference = stream.readUnsignedShort()
            val interfacesCount = stream.readUnsignedShort()
            val interfaces = ArrayList<ConstantReference>()
            repeat(interfacesCount) {
                interfaces.add(stream.readUnsignedShort())
            }
            val fieldsCount = stream.readUnsignedShort()
            val fields = ArrayList<Field>()
            repeat(fieldsCount) {
                fields.add(Field.readFromStream(stream, constantPool))
            }
            val methodsCount = stream.readUnsignedShort()
            val methods = ArrayList<Method>()
            repeat(methodsCount) {
                methods.add(Method.readFromStream(stream, constantPool))
            }

            val attributes = Attributes.readFromStream(stream, constantPool)

            return ClassFile(
                minorVersion,
                majorVersion,
                constantPool,
                accessFlags,
                constantPool.get(thisClass),
                constantPool.get(superClass),
                interfaces,
                fields,
                methods,
                attributes
            )
        }
    }
}