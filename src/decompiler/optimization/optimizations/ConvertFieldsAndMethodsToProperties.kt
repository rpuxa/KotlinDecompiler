package decompiler.optimization.optimizations

import decompiler.ControlList
import decompiler.KotlinClass
import decompiler.KotlinProperty
import decompiler.Type
import decompiler.optimization.Optimization

object ConvertFieldsAndMethodsToProperties : Optimization(true, true) {

    override fun optimize(clazz: KotlinClass) {
        val properties = HashMap<String, KotlinProperty>()
        clazz.fields.forEach {
            properties[it.field.name.value] = KotlinProperty(it, null, null)
        }
        val functions =  ControlList(clazz.functions)
        while (!functions.outBound()) {
            val function = functions.current()
            val name = function.method.name.value
            if (name.startsWith("get") && function.signature.arguments.isEmpty() && function.signature.returnType != Type.UNIT) {
                val backingFieldName = name[3].toLowerCase() + name.substring(4)
                val property = properties[backingFieldName]
                if (property == null) {
                    properties[backingFieldName] = KotlinProperty(null, function, null)
                    functions.delete()
                    continue
                } else if (property.backingField?.signature?.returnType == function.signature.returnType ||
                    property.setter?.signature?.arguments?.first() == function.signature.returnType
                ) {
                    property.getter = function
                    functions.delete()
                    continue
                }
            }
            functions.moveForward()
        }

        functions.reset()

        while (!functions.outBound()) {
            val function = functions.current()
            val name = function.method.name.value
            if (name.startsWith("set") && function.signature.arguments.size == 1 && function.signature.returnType == Type.UNIT) {
                val backingFieldName = name[3].toLowerCase() + name.substring(4)
                val property = properties[backingFieldName]
                if (property != null && property.getter?.signature?.returnType == function.signature.arguments.first()) {
                    property.setter = function
                }
            }

            functions.moveForward()
        }

        clazz.properties.clear()
        properties.values.forEach {
            clazz.properties.add(it)
        }
    }
}

