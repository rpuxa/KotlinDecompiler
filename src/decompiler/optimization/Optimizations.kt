package decompiler.optimization

import decompiler.KotlinClass
import decompiler.optimization.optimizations.*


object Optimizations {

    private val ALL = arrayOf(
        RemoveRedudantReturnInVoidFunctions,
        SelectKotlinClassType,
        SetVariableDeclarations,
        InlineSingleVariables,
        ConvertFieldsAndMethodsToProperties,
        AddBackingFieldToGettersAndSetters,
        RemoveRedundantGettersAndSetters,
        ConvertFunctionsToConstructors
    )

    fun optimize(clazz: KotlinClass) {
        ALL.forEach { it.optimize(clazz) }
    }
}