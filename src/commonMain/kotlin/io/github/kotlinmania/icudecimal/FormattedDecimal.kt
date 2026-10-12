// port-lint: source format.rs
@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.icudecimal

import io.github.kotlinmania.icudecimal.grouper.check
import io.github.kotlinmania.icudecimal.input.Decimal
import io.github.kotlinmania.icudecimal.options.DecimalFormatterOptions
import io.github.kotlinmania.icudecimal.options.GroupingStrategy
import io.github.kotlinmania.icudecimal.parts.DECIMAL
import io.github.kotlinmania.icudecimal.parts.FRACTION
import io.github.kotlinmania.icudecimal.parts.GROUP
import io.github.kotlinmania.icudecimal.parts.INTEGER
import io.github.kotlinmania.icudecimal.parts.MINUS_SIGN
import io.github.kotlinmania.icudecimal.parts.PLUS_SIGN
import io.github.kotlinmania.icudecimal.provider.DecimalSymbols
import io.github.kotlinmania.writeable.Part
import kotlin.native.HiddenFromObjC

/**
 * An intermediate structure returned by [DecimalFormatter].
 *
 * Use [asString] or [toString] to render the formatted decimal to a string.
 * Callers can inspect semantic output parts with [toParts].
 */
class FormattedDecimal internal constructor(
    internal val value: Decimal,
    internal val options: DecimalFormatterOptions,
    internal val symbols: DecimalSymbols,
    internal val digits: List<Char>,
) {
    /**
     * Returns the affixes needed for the current sign, as (part, (prefix, suffix)).
     */
    internal fun getAffixes(): Pair<Part, Pair<String, String>>? {
        val hasSign = value.isNegative && !value.isZero
        return if (hasSign) {
            val affixes = symbols.minusSignAffixes()
            signPart(isNegative = true) to (affixes.prefix to affixes.suffix)
        } else {
            null
        }
    }

    /**
     * Writes formatted decimal with parts representation into the provided builder.
     */
    internal fun writeToParts(builder: DecimalPartsBuilder) {
        val affixes = getAffixes()
        if (affixes != null) {
            builder.append(affixes.first, affixes.second.first)
        }
        val upperMagnitude = maxOf(value.upperMagnitude, 0)
        for (magnitude in upperMagnitude downTo 0) {
            builder.append(integerPart(), mappedDigit(value.digitAt(magnitude)))
            if (
                check(
                    upperMagnitude = upperMagnitude.toShort(),
                    magnitude = magnitude.toShort(),
                    strategy = options.groupingStrategy ?: GroupingStrategy.Auto,
                    sizes = symbols.groupingSizes,
                )
            ) {
                builder.append(groupPart(), symbols.groupingSeparator())
            }
        }
        if (value.magnitudeShift < 0) {
            builder.append(decimalPart(), symbols.decimalSeparator())
            for (magnitude in -1 downTo value.magnitudeShift) {
                builder.append(fractionPart(), mappedDigit(value.digitAt(magnitude)))
            }
        }
        if (affixes != null) {
            builder.append(affixes.first, affixes.second.second)
        }
    }

    private val rendered: Pair<String, List<FormattedDecimalPart>> by lazy {
        val builder = DecimalPartsBuilder()
        writeToParts(builder)
        builder.buildParts()
    }

    private fun mappedDigit(digit: Int): String = digits[digit].toString()

    /**
     * Returns the formatted decimal as a string.
     */
    fun asString(): String = rendered.first

    /**
     * Returns semantic ranges for the formatted decimal output.
     */
    @HiddenFromObjC
    fun toParts(): List<FormattedDecimalPart> = rendered.second

    override fun toString(): String = asString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormattedDecimal) return false
        return value == other.value &&
            options == other.options &&
            symbols == other.symbols &&
            digits == other.digits
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + options.hashCode()
        result = 31 * result + symbols.hashCode()
        result = 31 * result + digits.hashCode()
        return result
    }
}

/**
 * Semantic annotation for a range in formatted decimal output.
 */
data class FormattedDecimalPart(
    val part: Part,
    val start: Int,
    val end: Int,
)

internal class DecimalPartsBuilder {
    private val text = StringBuilder()
    private val segments = mutableListOf<FormattedDecimalPart>()

    fun append(part: Part, value: String) {
        if (value.isEmpty()) {
            return
        }
        val start = text.length
        text.append(value)
        segments +=
            FormattedDecimalPart(
                part = part,
                start = start,
                end = text.length,
            )
    }

    fun buildParts(): Pair<String, List<FormattedDecimalPart>> =
        text.toString() to segments.toList()
}

internal fun signPart(isNegative: Boolean): Part =
    if (isNegative) {
        MINUS_SIGN
    } else {
        PLUS_SIGN
    }

internal fun integerPart(): Part = INTEGER

internal fun fractionPart(): Part = FRACTION

internal fun groupPart(): Part = GROUP

internal fun decimalPart(): Part = DECIMAL
