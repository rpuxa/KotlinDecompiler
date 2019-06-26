package decompiler.optimization

import decompiler.KotlinClass
import decompiler.optimization.optimizations.*


object Optimizations {

    private val ALL = arrayOf(
        RemoveRedundantReturnInVoidFunctions,
        SelectKotlinClassType,
        ConvertTempVariablesToVariables,
      //  InlineSingleVariables,
        ConvertFieldsAndMethodsToProperties,
        AddBackingFieldToGettersAndSetters,
        RemoveRedundantGettersAndSetters,
        ConvertFunctionsToConstructors,
        RemoveRedundantCast,
        ReplaceVarWithVal
    )

    fun optimize(clazz: KotlinClass) {
        ALL.forEach { it.optimize(clazz) }
    }
}