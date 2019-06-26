package classfile.attribute.codeattribute

import classfile.attribute.codeattribute.arguments.*
import classfile.attribute.codeattribute.arguments.ByteArgument.Companion.FIVE
import classfile.attribute.codeattribute.arguments.ByteArgument.Companion.FOUR
import classfile.attribute.codeattribute.arguments.ByteArgument.Companion.M_ONE
import classfile.attribute.codeattribute.arguments.ByteArgument.Companion.ONE
import classfile.attribute.codeattribute.arguments.ByteArgument.Companion.THREE
import classfile.attribute.codeattribute.arguments.ByteArgument.Companion.TWO
import classfile.attribute.codeattribute.arguments.ByteArgument.Companion.ZERO
import java.io.DataInputStream
import kotlin.test.fail

enum class InstructionTypes(private val code: Int) {


    AALOAD(0x32),
    AASTORE(0x53),
    ACONST_NULL(0x01),
    ALOAD(0x19) {
        override fun DataInputStream.arg() = ubyte

        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = LOAD to arg
    },
    ALOAD_0(0x2a) {
        override fun convertTo() = LOAD to ZERO
    },
    ALOAD_1(0x2b) {
        override fun convertTo() = LOAD to ONE
    },
    ALOAD_2(0x2c) {
        override fun convertTo() = LOAD to TWO
    },
    ALOAD_3(0x2d) {
        override fun convertTo() = LOAD to THREE
    },
    ANEWARRAY(0xbd),
    ARETURN(0xb0) {
        override fun convertTo() = RETURN_SOMETHING to NoArgument
    },
    ARRAYLENGTH(0xbe),
    ASTORE(0x3a) {
        override fun DataInputStream.arg() = ubyte

        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = STORE to arg
    },
    ASTORE_0(0x4b) {
        override fun convertTo() = STORE to ZERO
    },
    ASTORE_1(0x4c) {
        override fun convertTo() = STORE to ONE
    },
    ASTORE_2(0x4d) {
        override fun convertTo() = STORE to TWO
    },
    ASTORE_3(0x4e) {
        override fun convertTo() = STORE to THREE
    },
    ATHROW(0xbf),
    BALOAD(0x33),
    BASTORE(0x54),
    BIPUSH(0x10){
        override fun DataInputStream.arg() = byte
    },
    BREAKPOINT(0xca),
    CALOAD(0x34),
    CASTORE(0x55),
    CHECKCAST(0xc0) {
        override fun DataInputStream.arg() = ushort
    },
    D2F(0x90),
    D2I(0x8e),
    D2L(0x8f),
    DADD(0x63),
    DALOAD(0x31),
    DASTORE(0x52),
    DCMPG(0x98),
    DCMPL(0x97),
    DCONST_0(0x0e){
        override fun convertTo() = DCONST to ZERO
    },
    DCONST_1(0x0f){
        override fun convertTo() = DCONST to ONE
    },
    DDIV(0x6f),
    DLOAD(0x18) {
        override fun DataInputStream.arg() = ubyte

        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = LOAD to arg
    },
    DLOAD_0(0x26) {
        override fun convertTo() = LOAD to ZERO
    },
    DLOAD_1(0x27) {
        override fun convertTo() = LOAD to ONE
    },
    DLOAD_2(0x28) {
        override fun convertTo() = LOAD to TWO
    },
    DLOAD_3(0x29) {
        override fun convertTo() = LOAD to THREE
    },
    DMUL(0x6b),
    DNEG(0x77),
    DREM(0x73),
    DRETURN(0xaf) {
        override fun convertTo() = RETURN_SOMETHING to NoArgument
    },
    DSTORE(0x39) {
        override fun DataInputStream.arg() = ubyte

        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = STORE to arg
    },
    DSTORE_0(0x47) {
        override fun convertTo() = STORE to ZERO
    },
    DSTORE_1(0x48) {
        override fun convertTo() = STORE to ONE
    },
    DSTORE_2(0x49) {
        override fun convertTo() = STORE to TWO
    },
    DSTORE_3(0x4a) {
        override fun convertTo() = STORE to THREE
    },
    DSUB(0x67),
    DUP(0x59),
    DUP_X1(0x5a),
    DUP_X2(0x5b),
    DUP2(0x5c),
    DUP2_X1(0x5d),
    DUP2_X2(0x5e),
    F2D(0x8d),
    F2I(0x8b),
    F2L(0x8c),
    FADD(0x62),
    FALOAD(0x30),
    FASTORE(0x51),
    FCMPG(0x96),
    FCMPL(0x95),
    FCONST_0(0x0b){
        override fun convertTo() = FCONST to ZERO
    },
    FCONST_1(0x0c){
        override fun convertTo() = FCONST to ONE
    },
    FCONST_2(0x0d){
        override fun convertTo() = FCONST to TWO
    },
    FDIV(0x6e),
    FLOAD(0x17) {
        override fun DataInputStream.arg() = ubyte

        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = LOAD to arg
    },
    FLOAD_0(0x22) {
        override fun convertTo() = LOAD to ZERO
    },
    FLOAD_1(0x23) {
        override fun convertTo() = LOAD to ONE
    },
    FLOAD_2(0x24) {
        override fun convertTo() = LOAD to TWO
    },
    FLOAD_3(0x25) {
        override fun convertTo() = LOAD to THREE
    },
    FMUL(0x6a),
    FNEG(0x76),
    FREM(0x72),
    FRETURN(0xae) {
        override fun convertTo() = RETURN_SOMETHING to NoArgument
    },
    FSTORE(0x38) {
        override fun DataInputStream.arg() = ubyte

        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = STORE to arg
    },
    FSTORE_0(0x43) {
        override fun convertTo() = STORE to ZERO
    },
    FSTORE_1(0x44) {
        override fun convertTo() = STORE to ONE
    },
    FSTORE_2(0x45) {
        override fun convertTo() = STORE to TWO
    },
    FSTORE_3(0x46) {
        override fun convertTo() = STORE to THREE
    },
    FSUB(0x66),
    GETFIELD(0xb4) {
        override fun DataInputStream.arg() = ushort
    },
    GETSTATIC(0xb2) {
        override fun DataInputStream.arg() = ushort
    },
    GOTO(0xa7) {
        override fun DataInputStream.arg() = Jump(Condition.NO_CONDITION, short.value)

        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = GOTO_W to arg
    },
    GOTO_W(0xc8) {
        override fun DataInputStream.arg() = Jump(Condition.NO_CONDITION, int.value)
    },
    I2B(0x91),
    I2C(0x92),
    I2D(0x87),
    I2F(0x86),
    I2L(0x85),
    I2S(0x93),
    IADD(0x60),
    IALOAD(0x2e),
    IAND(0x7e),
    IASTORE(0x4f),
    ICONST_M1(0x02) {
        override fun convertTo() = ICONST to M_ONE
    },
    ICONST_0(0x03) {
        override fun convertTo() = ICONST to ZERO
    },
    ICONST_1(0x04) {
        override fun convertTo() = ICONST to ONE
    },
    ICONST_2(0x05) {
        override fun convertTo() = ICONST to TWO
    },
    ICONST_3(0x06) {
        override fun convertTo() = ICONST to THREE
    },
    ICONST_4(0x07) {
        override fun convertTo() = ICONST to FOUR
    },
    ICONST_5(0x08) {
        override fun convertTo() = ICONST to FIVE
    },
    IDIV(0x6c),
    IF_ACMPEQ(0xa5) {
        override fun DataInputStream.arg() = condition(Condition.EQUAL_REFERENCES)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg
    },
    IF_ACMPNE(0xa6) {
        override fun DataInputStream.arg() = condition(Condition.NOT_EQUAL_REFERENCES)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg

    },
    IF_ICMPEQ(0x9f) {
        override fun DataInputStream.arg() = condition(Condition.EQUAL_INTS)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg

    },
    IF_ICMPGE(0xa2) {
        override fun DataInputStream.arg() = condition(Condition.GREATER_OR_EQUAL_INT)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg

    },
    IF_ICMPGT(0xa3) {
        override fun DataInputStream.arg() = condition(Condition.GREATER_INT)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg

    },
    IF_ICMPLE(0xa4) {
        override fun DataInputStream.arg() = condition(Condition.LESS_OR_EQUAL_INT)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg

    },
    IF_ICMPLT(0xa1) {
        override fun DataInputStream.arg() = condition(Condition.LESS_INT)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg

    },
    IF_ICMPNE(0xa0) {
        override fun DataInputStream.arg() = condition(Condition.NOT_EQUAL_INTS)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg

    },
    IFEQ(0x99) {
        override fun DataInputStream.arg() = condition(Condition.IS_ZERO)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg

    },
    IFGE(0x9c) {
        override fun DataInputStream.arg() = condition(Condition.IS_NOT_NEGATIVE)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg

    },
    IFGT(0x9d) {
        override fun DataInputStream.arg() = condition(Condition.IS_POSITIVE)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg

    },
    IFLE(0x9e) {
        override fun DataInputStream.arg() = condition(Condition.IS_NOT_POSITIVE)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg

    },
    IFLT(0x9b) {
        override fun DataInputStream.arg() = condition(Condition.IS_NEGATIVE)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg

    },
    IFNE(0x9a) {
        override fun DataInputStream.arg() = condition(Condition.IS_NOT_ZERO)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg
    },
    IFNONNULL(0xc7) {
        override fun DataInputStream.arg() = condition(Condition.IS_NOT_NULL)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg
    },
    IFNULL(0xc6) {
        override fun DataInputStream.arg() = condition(Condition.IS_NULL)
        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = IF to arg
    },
    IINC(0x84),
    ILOAD(0x15) {
        override fun DataInputStream.arg() = ubyte

        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = LOAD to arg
    },
    ILOAD_0(0x1a) {
        override fun convertTo() = LOAD to ZERO
    },
    ILOAD_1(0x1b) {
        override fun convertTo() = LOAD to ONE
    },
    ILOAD_2(0x1c) {
        override fun convertTo() = LOAD to TWO
    },
    ILOAD_3(0x1d) {
        override fun convertTo() = LOAD to THREE
    },
    IMPDEP1(0xfe),
    IMPDEP2(0xff),
    IMUL(0x68),
    INEG(0x74),
    INSTANCEOF(0xc1),
    INVOKEDYNAMIC(0xba),
    INVOKEINTERFACE(0xb9),
    INVOKESPECIAL(0xb7){
        override fun DataInputStream.arg() = ushort
    },
    INVOKESTATIC(0xb8) {
        override fun DataInputStream.arg() = ushort
    },
    INVOKEVIRTUAL(0xb6){
        override fun DataInputStream.arg() = ushort
    },
    IOR(0x80),
    IREM(0x70),
    IRETURN(0xac) {
        override fun convertTo() = RETURN_SOMETHING to NoArgument
    },
    ISHL(0x78),
    ISHR(0x7a),
    ISTORE(0x36) {
        override fun DataInputStream.arg() = ubyte

        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = STORE to arg
    },
    ISTORE_0(0x3b) {
        override fun convertTo() = STORE to ZERO
    },
    ISTORE_1(0x3c) {
        override fun convertTo() = STORE to ONE
    },
    ISTORE_2(0x3d) {
        override fun convertTo() = STORE to TWO
    },
    ISTORE_3(0x3e) {
        override fun convertTo() = STORE to THREE
    },
    ISUB(0x64),
    IUSHR(0x7c),
    IXOR(0x82),
    JSR(0xa8),
    JSR_W(0xc9),
    L2D(0x8a),
    L2F(0x89),
    L2I(0x88),
    LADD(0x61),
    LALOAD(0x2f),
    LAND(0x7f),
    LASTORE(0x50),
    LCMP(0x94),
    LCONST_0(0x09){
        override fun convertTo() = LCONST to ZERO
    },
    LCONST_1(0x0a) {
        override fun convertTo() = LCONST to ONE
    },
    LDC(0x12) {
        override fun DataInputStream.arg() = ubyte

        override fun convertTo(s: DataInputStream, arg: InstructionArgument) =
            LDC_W to ShortArgument((arg as ByteArgument).value)
    },
    LDC_W(0x13) {
        override fun DataInputStream.arg() = ushort
    },
    LDC2_W(0x14) {
        override fun DataInputStream.arg() = ushort

        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = LDC_W to arg
    },
    LDIV(0x6d),
    LLOAD(0x16) {
        override fun DataInputStream.arg() = ubyte

        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = LOAD to arg
    },
    LLOAD_0(0x1e) {
        override fun convertTo() = LOAD to ZERO
    },
    LLOAD_1(0x1f) {
        override fun convertTo() = LOAD to ONE
    },
    LLOAD_2(0x20) {
        override fun convertTo() = LOAD to TWO
    },
    LLOAD_3(0x21) {
        override fun convertTo() = LOAD to THREE
    },
    LMUL(0x69),
    LNEG(0x75),
    LOOKUPSWITCH(0xab),
    LOR(0x81),
    LREM(0x71),
    LRETURN(0xad) {
        override fun convertTo() = RETURN_SOMETHING to NoArgument
    },
    LSHL(0x79),
    LSHR(0x7b),
    LSTORE(0x37) {
        override fun DataInputStream.arg() = ubyte

        override fun convertTo(s: DataInputStream, arg: InstructionArgument) = STORE to arg
    },
    LSTORE_0(0x3f) {
        override fun convertTo() = STORE to ZERO
    },
    LSTORE_1(0x40) {
        override fun convertTo() = STORE to ONE
    },
    LSTORE_2(0x41) {
        override fun convertTo() = STORE to TWO
    },
    LSTORE_3(0x42) {
        override fun convertTo() = STORE to THREE
    },
    LSUB(0x65),
    LUSHR(0x7d),
    LXOR(0x83),
    MONITORENTER(0xc2),
    MONITOREXIT(0xc3),
    MULTIANEWARRAY(0xc5),
    NEW(0xbb) {
        override fun DataInputStream.arg() = ushort
    },
    NEWARRAY(0xbc),
    NOP(0x00),
    POP(0x57),
    POP2(0x58),
    PUTFIELD(0xb5) {
        override fun DataInputStream.arg() = ushort
    },
    PUTSTATIC(0xb3) {
        override fun DataInputStream.arg() = ushort
    },
    RET(0xa9),
    RETURN(0xb1),
    SALOAD(0x35),
    SASTORE(0x56),
    SIPUSH(0x11) {
        override fun DataInputStream.arg() = short
    },
    SWAP(0x5f),
    TABLESWITCH(0xaa),


    // My Instructions
    ICONST(-1),
    LCONST(-1),
    FCONST(-1),
    DCONST(-1),
    STORE(-1),
    RETURN_SOMETHING(-1),
    IF(-1),
    LABEL(-1),
    LOAD(-1),
    BREAK(-1),
    CONTINUE(-1),
    WHILE_LOOP_CONDITION(-1),
    ;


    fun readArgumentFromStream(stream: DataInputStream): InstructionArgument {
        return stream.arg()
    }

    open fun convertTo(s: DataInputStream, arg: InstructionArgument) = convertTo()



    protected open fun DataInputStream.arg(): InstructionArgument {
        return NoArgument
    }

    protected val DataInputStream.byte get() = ByteArgument(readByte().toInt())

    protected val DataInputStream.ubyte get() = ByteArgument(readUnsignedByte())

    protected val DataInputStream.short get() = ShortArgument(readShort().toInt())

    protected val DataInputStream.ushort get() = ShortArgument(readUnsignedShort())

    protected val DataInputStream.int get() = IntArgument(readInt())

    protected fun DataInputStream.condition(type: Condition) = Jump(type, readShort().toInt())


    protected open fun convertTo(): Pair<InstructionTypes, InstructionArgument>? = null

    companion object {
        private val array = Array(256) { code -> values().find { it.code == code } }

        fun valueOf(code: Int): InstructionTypes = array[code] ?: fail("Unknown instruction")
    }
}





























