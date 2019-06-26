package decompiler

import java.util.*
import kotlin.collections.HashSet

interface ClassInformer {

    fun findClassParents(reference: ClassReference): Set<ClassReference>

    fun findAllParents(reference: ClassReference): Set<ClassReference> {
        val result = HashSet<ClassReference>()
        val deque = ArrayDeque<ClassReference>()
        deque.addLast(reference)
        result.add(reference)
        while (deque.isNotEmpty()) {
            val ref = deque.pollFirst()!!
            val elements = findClassParents(ref).filter { it !in result }
            deque.addAll(elements)
            result.addAll(elements)
        }

        return result
    }
}