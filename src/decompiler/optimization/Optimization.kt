package decompiler.optimization

import decompiler.Block

abstract class Optimization(val optional: Boolean, val single: Boolean) {

    abstract fun optimize(block: Block)
}