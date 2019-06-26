package decompiler.optimization.optimizations

import decompiler.ElementController
import decompiler.elements.Assignment
import decompiler.elements.Block
import decompiler.elements.DeclarationAssignment
import decompiler.elements.Variable
import decompiler.optimization.Optimization

object ReplaceVarWithVal : Optimization(true, true) {

    override fun optimize(block: Block) {

        val controller = ElementController(block)
        val declarations = HashMap<Variable, DeclarationAssignment>()
        val moreThenOneAssignment = HashMap<Variable, Boolean>()

        controller.forEach {
            if (it is DeclarationAssignment) {
                declarations[it.variable] = it
                moreThenOneAssignment[it.variable] = false
            } else if (it is Assignment) {
                moreThenOneAssignment[it.variable] = true
            }
        }

        moreThenOneAssignment.forEach { (variable, moreThanOne) ->
            if (!moreThanOne)
                declarations[variable]!!.final = true
        }
    }
}