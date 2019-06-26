package decompiler.elements

import decompiler.Renderable
import decompiler.Type

interface Element : Renderable {
    val type: Type
}