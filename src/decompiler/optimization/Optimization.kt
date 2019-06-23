package decompiler.optimization

import decompiler.KotlinClass
import decompiler.elements.Block
import decompiler.elements.Element
import decompiler.KotlinFunction
import decompiler.KotlinProperty

abstract class Optimization(val optional: Boolean, val single: Boolean) {


    open fun optimize(clazz: KotlinClass) {
        clazz.functions.forEach(::optimize)
        clazz.properties.forEach(::optimize)
    }

    open fun optimize(property: KotlinProperty) {
    }

    open fun optimize(func: KotlinFunction) = optimize(func.code)

    open fun optimize(block: Block) {
    }
}