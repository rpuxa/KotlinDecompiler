package decompiler.elements

import kotlin.test.fail

interface ComplexElement : Element {
    operator fun get(index: Int): Element {
        checkBound(index)
        return getByIndex(index)
    }

    fun replace(index: Int, element: Element) {
        checkBound(index)
        replaceByIndex(index, element)
    }


    fun getByIndex(index: Int): Element

    fun replaceByIndex(index: Int, element: Element)

    val size: Int

    fun outOfBound(index: Int): Nothing = fail("Index out of bound: $index")

    private fun checkBound(index: Int) {
        if (index < 0 || index > size)
            outOfBound(index)
    }
}