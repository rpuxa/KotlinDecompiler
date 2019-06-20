package classfile.attribute.codeattribute.arguments

import classfile.attribute.codeattribute.InstructionArgument

class IntArgument(val value: Int) : InstructionArgument {
    override val bytesCount get() = 4
}