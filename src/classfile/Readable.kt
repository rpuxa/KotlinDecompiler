package classfile

import classfile.constant.ConstantPool
import java.io.DataInputStream

interface Readable<out T> {
    fun readFromStream(stream: DataInputStream, pool: ConstantPool): T
}