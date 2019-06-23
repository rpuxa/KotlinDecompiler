package decompiler

class VariablesNames(variablesCount: Int) {
    private val variables = Array(variablesCount) { "var$it" }

    operator fun get(index: Int) = variables[index]
    operator fun set(index: Int, name: String) {
        variables[index] = name
    }
}