package decompiler

import classfile.attribute.attributes.LocalVariableTableAttribute
import decompiler.elements.Variable
import kotlin.test.fail

class Variables(arity: Int, localVariablesAttribute: LocalVariableTableAttribute?) {
    val arguments = (0 until arity).map { index ->
        localVariablesAttribute.let { variableTable ->
            if (variableTable == null) {
                Variable(getVariableName(index), index)
            } else {
                Variable(variableTable.getArgumentName(index), index)
            }
        }
    }.associateBy { it.index }

    fun getArgumentName(index: Int) = arguments[index] ?: fail()


   companion object {
       fun getVariableName(index: Int) = "var$index"
   }
}
