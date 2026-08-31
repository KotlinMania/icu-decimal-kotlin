package io.github.kotlinmania.icudecimal.input

/**
 * Decimal input accepted by [io.github.kotlinmania.icudecimal.DecimalFormatter].
 *
 * The value is stored as an integer coefficient and a base-10 magnitude shift.
 * For example, coefficient `200050` with magnitude `-2` formats as `2000.50`.
 */
class Decimal private constructor(
    internal val coefficient: Long,
    internal var magnitudeShift: Int,
) {
    internal val isZero: Boolean
        get() = coefficient == 0L

    internal val isNegative: Boolean
        get() = coefficient < 0L

    internal val absoluteDigits: String
        get() = coefficient.toString().removePrefix("-")

    internal val upperMagnitude: Int
        get() = magnitudeShift + absoluteDigits.length - 1

    /**
     * Multiply this decimal by `10^power`.
     */
    fun multiplyPow10(power: Short) {
        magnitudeShift += power.toInt()
    }

    /**
     * Multiply this decimal by `10^power`.
     */
    fun multiplyPow10(power: Int) {
        magnitudeShift += power
    }

    internal fun digitAt(magnitude: Int): Int {
        val index = upperMagnitude - magnitude
        if (index !in absoluteDigits.indices) {
            return 0
        }
        return absoluteDigits[index].code - '0'.code
    }

    companion object {
        /**
         * Create a decimal from a signed integer.
         */
        fun from(value: Long): Decimal = Decimal(value, 0)

        /**
         * Create a decimal from a signed integer.
         */
        fun from(value: Int): Decimal = Decimal(value.toLong(), 0)

        /**
         * Parse a decimal string using ASCII digits and an optional decimal
         * separator.
         */
        fun parse(value: String): Decimal {
            val trimmed = value.trim()
            require(trimmed.isNotEmpty()) { "Decimal string must not be empty" }

            val negative = trimmed.startsWith("-")
            val unsigned = trimmed.removePrefix("-").removePrefix("+")
            require(unsigned.count { it == '.' } <= 1) {
                "Decimal string has more than one decimal separator"
            }

            val separatorIndex = unsigned.indexOf('.')
            val integerPart =
                if (separatorIndex >= 0) {
                    unsigned.substring(0, separatorIndex)
                } else {
                    unsigned
                }
            val fractionPart =
                if (separatorIndex >= 0) {
                    unsigned.substring(separatorIndex + 1)
                } else {
                    ""
                }
            val digits = (integerPart + fractionPart).trimStart('0').ifEmpty { "0" }
            require(digits.all { it in '0'..'9' }) {
                "Decimal string must contain only ASCII digits"
            }

            val coefficient = digits.toLong()
            return Decimal(
                coefficient = if (negative && coefficient != 0L) -coefficient else coefficient,
                magnitudeShift = -fractionPart.length,
            )
        }
    }
}
