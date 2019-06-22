package decompiler

class Block(val elements: MutableList<Element>) : Element {

    override fun render(builder: CodeStringBuilder) {
        builder.addTab()
        elements.forEach {
            it.render(builder)
            builder.newLine()
        }
        builder.removeTab()
    }

    fun toControlList(): ControlList<Element> = ControlList(elements)
}