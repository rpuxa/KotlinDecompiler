package decompiler

class CodeStringBuilder {

    private val builder = StringBuilder()

    private var tabs = 0
    private var newLine = true

    fun removeTab(): CodeStringBuilder {
        tabs--

        return this
    }

    fun addTab(): CodeStringBuilder {
        tabs++

        return this
    }

    fun append(renderable: Renderable): CodeStringBuilder {
        renderable.render(this)

        return this
    }

    fun append(char: Char): CodeStringBuilder {
        prepare()
        builder.append(char)

        return this
    }

    fun append(string: String): CodeStringBuilder {
        prepare()
        builder.append(string)

        return this
    }

    fun append(int: Int): CodeStringBuilder {
        prepare()
        builder.append(int)

        return this
    }

    fun append(double: Double): CodeStringBuilder {
        prepare()
        builder.append(double)

        return this
    }

    fun append(float: Float): CodeStringBuilder {
        prepare()
        builder.append(float)

        return this
    }

    fun append(long: Long): CodeStringBuilder {
        prepare()
        builder.append(long)

        return this
    }

    fun append(boolean: Boolean): CodeStringBuilder {
        prepare()
        builder.append(boolean)

        return this
    }

    fun newLine(): CodeStringBuilder {
        append('\n')
        newLine = true

        return this
    }

    override fun toString() = builder.toString()

    private fun prepare() {
        if (newLine) {
            newLine = false
            repeat(tabs) {
                builder.append('\t')
            }
        }
    }
}