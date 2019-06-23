package decompiler.optimization.optimizations

import decompiler.elements.Element
import decompiler.ElementController
import decompiler.KotlinFunction
import decompiler.elements.*
import decompiler.optimization.Optimization
import java.util.*
import kotlin.collections.HashMap

object InlineSingleVariables : Optimization(true, true) {


    override fun optimize(func: KotlinFunction) {
        find(
            ElementController(func.code),
            Stack(),
            func.method.attributes.codeAttribute.maxLocals,
            func.signature.arguments.size
        )
    }

    private fun find(controller: ElementController, used: Stack<IntArray>, locals: Int, argumentsSize: Int) {
        val usedInThisBlock = IntArray(locals)
        used.push(usedInThisBlock)
        do {
            val element = controller.current()
            when {
                element is DeclarationAssignment -> {
                    controller.down()
                    find(controller, used, locals, argumentsSize)
                    controller.up()
                    usedInThisBlock[element.variableIndex]++
                }

                element is Variable || element is Assignment -> {
                    val index = if (element is Variable) element.index else (element as Assignment).variableIndex
                    if (index >= argumentsSize) {
                        for (array in used) {
                            if (array[index] != 0) {
                                array[index]++
                                break
                            }
                        }
                    }
                }
                controller.canDown() -> {
                    controller.down()
                    find(controller, used, locals, argumentsSize)
                    controller.up()
                }
            }
        } while (controller.right())

        used.pop()
        if (usedInThisBlock.all { it != 2 })
            return
        val map = HashMap<Int, Element?>()
        usedInThisBlock.forEachIndexed { index, u ->
            if (u == 2) {
                map[index] = null
            }
        }

        controller.reset()
        replace(controller, map, false)
    }

    private fun replace(controller: ElementController, toReplace: HashMap<Int, Element?>, innerBlock: Boolean) {
        loop@ while (true) {
            val element = controller.current()
            when {
                controller.canDown() -> {
                    controller.down()
                    replace(controller, toReplace, true)
                    controller.up()
                    if (!innerBlock && element is DeclarationAssignment && element.variableIndex in toReplace) {
                        toReplace[element.variableIndex] = element.element
                        controller.delete()
                        continue@loop
                    }
                }

                element is Variable -> {
                    val to = toReplace[element.index]
                    if (to != null)
                        controller.replace(to)
                }
            }
            if (!controller.right())
                break
        }
    }
}