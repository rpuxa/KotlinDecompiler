package decompiler.optimization.optimizations

import decompiler.KotlinProperty
import decompiler.elements.GetBackingField
import decompiler.elements.Return
import decompiler.elements.SetBackingField
import decompiler.elements.Variable
import decompiler.optimization.Optimization

object RemoveRedundantGettersAndSetters : Optimization(true, true) {

    override fun optimize(property: KotlinProperty) {
        val getterCode = property.getter?.code
        if (getterCode != null && getterCode.size == 1 && (getterCode[0] as? Return)?.element is GetBackingField) {
            property.getter = null
        }
        val setterCode = property.setter?.code
        if (setterCode != null &&
            setterCode.size == 1 &&
            ((setterCode[0] as? SetBackingField)?.element as? Variable)?.index == 1
        ) {
            property.setter = null
        }
    }
}