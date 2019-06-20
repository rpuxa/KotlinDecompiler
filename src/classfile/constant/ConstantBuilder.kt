package classfile.constant

import classfile.Readable

interface ConstantBuilder : Readable<Constant> {
    val tag: Int
}