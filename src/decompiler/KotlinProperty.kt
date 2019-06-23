package decompiler

class KotlinProperty(
    var backingField: KotlinField?,
    var getter: KotlinFunction?,
    var setter: KotlinFunction?,
    var mutable: Boolean = false
) : Renderable {

    val name get() = backingField?.field?.name?.value ?: getter!!.method.name.value
    val returnType get() = backingField?.signature?.returnType ?: getter!!.signature.returnType

    override fun render(builder: CodeStringBuilder) {
        builder.append(if (mutable) "var" else "val")
        builder.append(' ')
            .append(name)
            .append(": ")
            .append(returnType.name)
        val defaultValue = backingField?.defaultValue
        if (defaultValue != null) {
            builder.append(" = ")
            defaultValue.render(builder)
        }
        getter?.let { getter ->
            builder.newLine()
                .addTab()
                .append("get() {")
                .newLine()
            getter.code.render(builder)
            builder.append('}')
                .removeTab()
        }

        setter?.let { setter ->
            builder.newLine()
                .addTab()
                .append("set(setterValue) {")
                .newLine()
            setter.variablesNames[1] = "setterValue"
            setter.code.render(builder)
            builder.newLine()
                .append('}')
                .removeTab()
        }

        builder.newLine()
    }

}