import classfile.ClassFile
import decompiler.BlockBuilder
import decompiler.CodeStringBuilder
import decompiler.InstructionSequence
import java.io.DataInputStream
import java.io.FileInputStream

fun main() {
    FileInputStream("JavaClassKt.class").use {
        val stream = DataInputStream(it)
        val file = ClassFile.readFromStream(stream)
        val block = BlockBuilder.fromCodeSequence(
            file,
            InstructionSequence(file.methods[1].attributes.codeAttribute.code.toMutableList())
        )
        val builder = CodeStringBuilder()
        block.render(builder)
        println(builder)
    }
}