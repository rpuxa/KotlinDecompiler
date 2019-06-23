package decompiler

import classfile.attribute.attributes.ConstantValueAttribute
import classfile.field.Field
import decompiler.elements.Element
import decompiler.elements.literals.Literals


class KotlinField(
    val field: Field

) /* is not Renderable */ {
    var defaultValue: Element? = null

    val signature = Signature.fromDescriptor(field.descriptor.value)


    init {
        val constantValue = field.attributes.getAttribute<ConstantValueAttribute>()?.value
        if (constantValue != null)
            defaultValue = Literals.fromConstant(constantValue)
    }
}