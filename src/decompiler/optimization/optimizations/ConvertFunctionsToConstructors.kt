package decompiler.optimization.optimizations

import decompiler.CallSuperConstructor
import decompiler.Constructor
import decompiler.KotlinClass
import decompiler.KotlinFunction
import decompiler.optimization.Optimization

object ConvertFunctionsToConstructors : Optimization(false, true) {

    override fun optimize(clazz: KotlinClass) {
        fun remove() = clazz.functions.removeIf { it.isConstructor() }

        if (clazz.type == KotlinClass.Types.OBJECT) {
            remove()
            return
        }

        val constructors = clazz.functions
            .filter { it.isConstructor() }
            .map {
                val c = it.code.elements.removeAt(0) as CallSuperConstructor
                val call = when (c.reference.clazz.name) {
                    "Object" -> Constructor.CALL_NOTHING
                    clazz.name -> Constructor.CALL_THIS_CONSTRUCTOR
                    else -> Constructor.CALL_SUPER_CONSTRUCTOR
                }
                Constructor(
                    it.method,
                    it.signature,
                    it.code,
                    c.arguments,
                    call,
                    it.variablesNames
                )
            }

        remove()

        clazz.constructors.addAll(constructors)
    }

    private fun KotlinFunction.isConstructor() = method.name.value == "<init>"
}