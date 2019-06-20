package classfile.attribute.codeattribute.arguments

import classfile.attribute.codeattribute.InstructionArgument

object NoArgument : InstructionArgument {
    override val bytesCount get() = 0
}