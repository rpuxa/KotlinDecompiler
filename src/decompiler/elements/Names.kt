package decompiler.elements

object Names {
    fun variable(index: Int): String {
        return "v$index"
    }

    fun loopLabel(index: Int):String = "loop$index"
}