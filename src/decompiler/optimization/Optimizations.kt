package decompiler.optimization

import decompiler.Block
import decompiler.KotlinClass
import decompiler.optimization.optimizations.SetVariableDeclarations
import java.io.File
import java.util.ArrayList


object Optimizations {

    private val ALL = arrayOf(
        SetVariableDeclarations
    )

    fun optimize(clazz: KotlinClass) {

    }

    fun optimize(block: Block) {
        ALL.forEach { it.optimize(block) }
    }
}