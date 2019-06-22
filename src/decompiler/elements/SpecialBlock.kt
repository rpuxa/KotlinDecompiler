package decompiler.elements

import decompiler.Block
import decompiler.Element

interface SpecialBlock : Element {
    val blocks: List<Block>
}