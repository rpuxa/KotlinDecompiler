package classfile.attribute.codeattribute.arguments

import classfile.attribute.codeattribute.Condition
import classfile.attribute.codeattribute.Instruction
import classfile.attribute.codeattribute.InstructionArgument
import kotlin.properties.Delegates

class Jump(val condition: Condition, val offset: Int) : InstructionArgument {

    override fun toString() = labelId.toString()

    var labelId: Int by Delegates.notNull()

    val jumpForward get() = offset > 0

    override val bytesCount get() = 2
}