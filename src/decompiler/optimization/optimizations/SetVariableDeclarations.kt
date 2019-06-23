package decompiler.optimization.optimizations

import decompiler.elements.Block
import decompiler.ElementController
import decompiler.KotlinFunction
import decompiler.VariablesNames
import decompiler.elements.Assignment
import decompiler.elements.DeclarationAssignment
import decompiler.optimization.Optimization

object SetVariableDeclarations : Optimization(false, true) {

    override fun optimize(func: KotlinFunction) {
        optimize(ElementController(func.code), HashSet(), func.variablesNames)
    }

    private fun optimize(controller: ElementController, set: HashSet<Int>, variablesNames: VariablesNames) {
        val copySet = HashSet(set)
        do {
            val element = controller.current()
            if (element is Assignment) {
                if (element.variableIndex !in copySet) {
                    copySet.add(element.variableIndex)
                    controller.replace(
                        DeclarationAssignment(
                            final = false,
                            showType = false,
                            variableIndex = element.variableIndex,
                            element = element.element,
                            variablesNames = variablesNames
                        )
                    )
                }
            } else if (controller.canDown()) {
                controller.down()
                optimize(controller, copySet, variablesNames)
                controller.up()
            }
        } while (controller.right())
    }
}