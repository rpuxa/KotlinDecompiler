package decompiler

import decompiler.elements.ComplexElement
import decompiler.elements.Element
import decompiler.elements.ElementSequence
import java.util.*
import kotlin.test.fail

class ElementController(
    element: ComplexElement
) {
    private val elementHierarchy = Stack<ComplexElement>().apply { push(element) }
    private val pointer = Stack<Int>().apply { push(0) }

    val currentParent get() = elementHierarchy.peek()
    val currentPosition get() = pointer.peek()

    fun down() {
        val complexElement = current() as? ComplexElement ?: fail("You are in leaf")
        if (complexElement.size == 0) fail("Complex element is empty")
        elementHierarchy.push(complexElement)
        pointer.push(0)
    }

    fun canUp() = pointer.size > 1

    fun canDown(): Boolean {
        val current = current()
        return current is ComplexElement && current.size > 0
    }

    fun up() {
        if (elementHierarchy.size == 1) fail("You are in root")
        elementHierarchy.pop()
        pointer.pop()
    }

    fun canRight(): Boolean = currentPosition >= currentParent.size

    fun right(times: Int = 1): Boolean {
        pointer.push(pointer.pop() + times)
        return checkBounds()
    }

    fun left(times: Int = 1): Boolean {
        pointer.push(pointer.pop() - times)
        return checkBounds()
    }

    fun current() = currentParent[currentPosition]

    fun replace(element: Element) = currentParent.replace(currentPosition, element)

    fun insert(element: Element) = asElementSequence().insert(currentPosition, element)

    fun delete() = asElementSequence().delete(currentPosition)
    private fun asElementSequence() = currentParent as? ElementSequence ?: fail("You are not in element sequence")

    private fun checkBounds(): Boolean {

        val pointer = pointer.peek()
        val element = elementHierarchy.peek()
        return pointer >= 0 && pointer < element.size
    }

    fun reset() {
        pointer[pointer.lastIndex] = 0
    }

    inline fun forEach(block: (Element) -> Unit) {
      loop@while (true) {
            val current = current()
            block(current)
            if (canDown()) {
                down()
            } else if (canRight()) {
                right()
            } else {
                while (true) {
                    if (canUp()) {
                        up()
                        if (canRight()) {
                            right()
                            break
                        }
                    } else {
                        break@loop
                    }
                }
            }
        }
    }
}