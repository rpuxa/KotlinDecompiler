package decompiler

object ReflectionClassInformer : ClassInformer {
    override fun findClassParents(reference: ClassReference): Set<ClassReference> {
        val clazz = Class.forName(reference.path.joinToString(".") + "." + reference.name)
        val set = HashSet<ClassReference>()
        val superclass = clazz.superclass
        if (superclass != null)
            set.add(ClassReference.valueOf(superclass))
        clazz.interfaces.forEach {
            set.add(ClassReference.valueOf(it))
        }

        return set
    }
}