import classfile.ClassFile
import decompiler.BlockBuilder
import decompiler.CodeStringBuilder
import decompiler.ControlList
import decompiler.optimization.Optimizations
import java.io.DataInputStream
import java.io.FileInputStream

fun main() {
    FileInputStream("C:\\Projects\\untitled2\\out\\production\\untitled2\\KotlinKt.class").use {
        val stream = DataInputStream(it)
        val file = ClassFile.readFromStream(stream)
        val block = BlockBuilder.fromCodeSequence(
            file.constantPool,
            ControlList(file.methods[0].attributes.codeAttribute.code.toMutableList())
        )
        Optimizations.optimize(block)
        val builder = CodeStringBuilder()
        block.render(builder)
        println(builder)
    }
}