package decompiler.optimization.optimizations

import classfile.attribute.attributes.LocalVariableTableAttribute
import decompiler.ElementController
import decompiler.KotlinFunction
import decompiler.Variables
import decompiler.elements.*
import decompiler.optimization.Optimization

object ConvertTempVariablesToVariables : Optimization(false, true) {

    override fun optimize(func: KotlinFunction) {
        find(
            func.variables,
            ElementController(func.code),
            func.variables.arguments
        )
    }

    private fun find(variables: Variables, sequence: ElementController, used: Map<Int, Variable>) {
        val usedInThisBlock = HashMap<Int, Variable>()

        fun get(index: Int) = used[index] ?: usedInThisBlock[index]

        do {
            val element = sequence.current()
            when {
                sequence.canDown() -> {
                    sequence.down()
                    find(variables, sequence, usedInThisBlock + used)
                    sequence.up()
                }

                element is TempAssignment -> {
                    val variable = get(element.index)
                    if (variable == null) {
                        val v = Variable(Variables.getVariableName(element.index), element.index)
                        v.addType(element.element.type)
                        usedInThisBlock[element.index] = v
                        sequence.replace(DeclarationAssignment(v, element.element, showType = true))
                    } else {
                        sequence.replace(Assignment(variable, element.element))
                    }
                }

                element is TempVariable -> {
                    val variable = get(element.index)!!
                    variable.usedTimes++
                    sequence.replace(variable)
                }
            }
        } while (sequence.right())
    }
}