package classfile.attribute.codeattribute.arguments

import classfile.attribute.codeattribute.InstructionArgument

class ShortArgument(val value: Int) : InstructionArgument {
    override val bytesCount get() = 2

    override fun toString() = value.toString()
}