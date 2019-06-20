package decompiler.elements

import decompiler.Element

interface ElementSequence : Element, Iterable<Element>