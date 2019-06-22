package classfile.constant

import classfile.ConstantReference
import classfile.constant.constants.ClassConstant
import classfile.constant.constants.DoubleConstant
import classfile.constant.constants.LongConstant
import classfile.constant.constants.UtfConstant
import java.io.DataInputStream
import kotlin.test.fail

class ConstantPool(private val constants: List<Constant?>) {

    fun getConstant(index: ConstantReference) = constants[index - 1]!!

    inline fun <reified T : Constant> get(index: ConstantReference) = getConstant(index) as T

    tailrec fun getName(index: ConstantReference): String =
        when (val constant = get<Constant>(index)) {
            is ClassConstant -> getName(constant.nameReference)
            is UtfConstant -> constant.value
            else -> fail("Cant extract name")
        }

    companion object {

        fun readFromStream(stream: DataInputStream): ConstantPool {
            val count = stream.readUnsignedShort()
            val list = ArrayList<Constant?>()
            val pool = ConstantPool(list)
            var i = 0
            while (i < count - 1) {
                val constant = Constant.readFromStream(stream, pool)
                list.add(constant)
                if (constant is LongConstant || constant is DoubleConstant){
                    i++
                    list.add(null)
                }
                i++
            }
            return pool
        }
    }

}