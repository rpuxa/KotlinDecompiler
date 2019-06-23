package decompiler.optimization.optimizations

import decompiler.ElementController
import decompiler.KotlinProperty
import decompiler.elements.*
import decompiler.optimization.Optimization

object AddBackingFieldToGettersAndSetters : Optimization(false, true) {

    override fun optimize(property: KotlinProperty) {
        arrayOf(property.getter, property.setter).forEach { function ->
            if (function == null) return@forEach
            val controller = ElementController(function.code)
            controller.forEach { element ->
                if (element is GetField && (element.obj as? Variable)?.index == 0 && element.ref.name == property.name) {
                    controller.replace(GetBackingField)
                } else if (element is SetField && (element.obj as? Variable)?.index == 0 && element.ref.name == property.name) {
                    controller.replace(SetBackingField(element.element))
                }
            }
        }
    }
}