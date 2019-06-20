package classfile.attribute.codeattribute

enum class Condition(val arity: Int) {
    NO_CONDITION(0),
    EQUAL_REFERENCES(2),
    NOT_EQUAL_REFERENCES(2),
    EQUAL_INTS(2 ),
    NOT_EQUAL_INTS(2),
    GREATER_OR_EQUAL_INT(2),
    GREATER_INT(2),
    LESS_OR_EQUAL_INT(2),
    LESS_INT(2),
    IS_ZERO(1),
    IS_NOT_ZERO(1),
    IS_NOT_NEGATIVE(1),
    IS_POSITIVE(1),
    IS_NEGATIVE(1),
    IS_NOT_POSITIVE(1),
    IS_NULL(1),
    IS_NOT_NULL(1)
}