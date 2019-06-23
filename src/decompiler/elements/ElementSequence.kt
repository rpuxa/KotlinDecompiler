package decompiler.elements

interface ElementSequence : ComplexElement {
    fun insert(index: Int, element: Element)
    fun delete(index: Int)
}