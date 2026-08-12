// port-lint: source format.rs
@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.icudecimal

import io.github.kotlinmania.icudecimal.parts.DECIMAL
import io.github.kotlinmania.icudecimal.parts.FRACTION
import io.github.kotlinmania.icudecimal.parts.GROUP
import io.github.kotlinmania.icudecimal.parts.INTEGER
import io.github.kotlinmania.icudecimal.parts.MINUS_SIGN
import io.github.kotlinmania.icudecimal.parts.PLUS_SIGN
import io.github.kotlinmania.writeable.Part
import kotlin.native.HiddenFromObjC

/**
 * An intermediate structure returned by [DecimalFormatter].
 *
 * Use [toString] to render the formatted decimal. Kotlin callers can inspect
 * semantic output parts with [toParts].
 */
class FormattedDecimal internal constructor(
    private val text: String,
    private val segments: List<FormattedDecimalPart>,
) {
    /**
     * Returns the formatted decimal as a string.
     */
    fun asString(): String = text

    /**
     * Returns semantic ranges for the formatted decimal output.
     */
    @HiddenFromObjC
    fun toParts(): List<FormattedDecimalPart> = segments

    override fun toString(): String = text
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

    fun build(): FormattedDecimal =
        FormattedDecimal(
            text = text.toString(),
            segments = segments.toList(),
        )
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
