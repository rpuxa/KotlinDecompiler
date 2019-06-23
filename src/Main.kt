import classfile.ClassFile
import decompiler.*
import decompiler.optimization.Optimizations
import java.io.DataInputStream
import java.io.FileInputStream

fun main() {
    FileInputStream("C:\\Projects\\untitled2\\out\\production\\untitled2\\Kotlin.class").use {
        val stream = DataInputStream(it)
        val file = ClassFile.readFromStream(stream)
        val clazz = KotlinClass.fromClassFile(file)
        val builder = CodeStringBuilder()
        clazz.render(builder)
        println(builder)
    }
}