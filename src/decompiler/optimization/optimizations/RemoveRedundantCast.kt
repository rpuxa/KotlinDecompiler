package decompiler.optimization.optimizations

import decompiler.ElementController
import decompiler.ReflectionClassInformer
import decompiler.elements.Block
import decompiler.elements.Cast
import decompiler.optimization.Optimization

object RemoveRedundantCast : Optimization(true, true) {

    override fun optimize(block: Block) {
        val controller = ElementController(block)
        controller.forEach {
            if (it is Cast) {
                val parents = ReflectionClassInformer.findAllParents(it.element.type.reference)
                if (it.ref in parents) {
                    controller.replace(it.element)
                }
            }
        }
    }
}