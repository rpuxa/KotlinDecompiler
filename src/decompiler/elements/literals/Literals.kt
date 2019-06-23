package decompiler.elements.literals

import classfile.constant.Constant
import classfile.constant.constants.IntConstant
import classfile.constant.constants.StringConstant
import decompiler.elements.Element

object Literals {

    fun fromConstant(constant: Constant): Element =
        when (constant) {
            is StringConstant -> StringLiteral(constant.value)
            is IntConstant -> IntLiteral(constant.value)
            else -> TODO("other constants")
        }
}