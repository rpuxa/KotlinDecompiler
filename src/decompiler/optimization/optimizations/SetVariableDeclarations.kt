package decompiler.optimization.optimizations

import decompiler.Block
import decompiler.elements.Assignment
import decompiler.elements.DeclarationAssigment
import decompiler.elements.SpecialBlock
import decompiler.optimization.Optimization

object SetVariableDeclarations : Optimization(false, true) {

    override fun optimize(block: Block) {
        optimize(block, HashSet())
    }

    private fun optimize(block: Block, set: HashSet<Int>) {
        val list = block.toControlList()
        val copySet = HashSet(set)
        list.forEach { element ->
            if (element is Assignment && element.variableIndex !in copySet) {
                copySet.add(element.variableIndex)
                list.replace(DeclarationAssigment(
                    final = false,
                    showType = false,
                    variableIndex = element.variableIndex,
                    element = element.element
                ))
            }
            if (element is Block) {
                optimize(element, copySet)
            } else if (element is SpecialBlock) {
                element.blocks.forEach { optimize(it, copySet) }
            }
        }
    }
}