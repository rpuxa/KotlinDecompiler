package decompiler.optimization.optimizations

import decompiler.KotlinFunction
import decompiler.elements.Return
import decompiler.optimization.Optimization

object RemoveRedudantReturnInVoidFunctions : Optimization(true, true) {

    override fun optimize(func: KotlinFunction) {
        val elements = func.code.elements
        val last = elements.last()
        if (last is Return && last.element == null) {
            elements.removeAt(elements.lastIndex)
        }
    }
}