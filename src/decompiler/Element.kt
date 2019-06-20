package decompiler

interface Element {
    fun render(builder: CodeStringBuilder)
}