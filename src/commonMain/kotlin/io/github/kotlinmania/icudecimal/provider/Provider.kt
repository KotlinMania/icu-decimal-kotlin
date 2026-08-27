// port-lint: source icu_decimal/src/provider.rs
package io.github.kotlinmania.icudecimal.provider

/**
 * Unstable data provider struct definitions for this ICU4X component.
 *
 * This code is considered unstable; it may change at any time, in breaking or
 * non-breaking ways, including in SemVer minor releases. While the serialized
 * representation of data structs is guaranteed to be stable, their Kotlin
 * representation might not be. Use with caution.
 */

/**
 * Baked data.
 *
 * This code is considered unstable; it may change at any time, in breaking or
 * non-breaking ways, including in SemVer minor releases. In particular, the
 * data provider implementations are only guaranteed to match with this
 * version's unstable providers. Use with caution.
 */
data object Baked

/**
 * Data marker for decimal symbols.
 */
data object DecimalSymbolsV1 {
    const val KEY: String = "decimal/symbols/v1"
}

/**
 * The digits for a given numbering system. This data ought to be stored in the
 * `und` locale with an auxiliary key set to the numbering system code.
 */
data object DecimalDigitsV1 {
    const val KEY: String = "decimal/digits/v1"
    const val ATTRIBUTES_DOMAIN: String = "numbering_system"
}

/**
 * The latest minimum set of markers required by this component.
 */
val MARKERS: List<String> = listOf(DecimalSymbolsV1.KEY, DecimalDigitsV1.KEY)

/**
 * A collection of settings expressing where to put grouping separators in a
 * decimal number. For example, `1,000,000` has two grouping separators,
 * positioned along every 3 digits.
 *
 * This code is considered unstable; it may change at any time, in breaking or
 * non-breaking ways, including in SemVer minor releases. While the serialized
 * representation of data structs is guaranteed to be stable, their Kotlin
 * representation might not be. Use with caution.
 */
data class GroupingSizes(
    /**
     * The size of the first lowest-magnitude group.
     *
     * If 0, grouping separators will never be shown.
     */
    val primary: UByte,
    /**
     * The size of groups after the first group.
     *
     * If 0, defaults to be the same as [primary].
     */
    val secondary: UByte,
    /**
     * The minimum number of digits required before the first group. For example,
     * if [primary] is 3 and [minGrouping] is 2, grouping separators will be
     * present on 10,000 and above.
     */
    val minGrouping: UByte,
)

/**
 * A stack representation of the strings used in [DecimalSymbols], meaning a
 * builder type for [DecimalSymbolStrs]. This type can be obtained from
 * [DecimalSymbolStrs].
 *
 * This code is considered unstable; it may change at any time, in breaking or
 * non-breaking ways, including in SemVer minor releases. While the serialized
 * representation of data structs is guaranteed to be stable, their Kotlin
 * representation might not be. Use with caution.
 */
data class DecimalSymbolStrsBuilder(
    /**
     * Prefix to apply when a negative sign is needed.
     */
    val minusSignPrefix: String,
    /**
     * Suffix to apply when a negative sign is needed.
     */
    val minusSignSuffix: String,
    /**
     * Prefix to apply when a positive sign is needed.
     */
    val plusSignPrefix: String,
    /**
     * Suffix to apply when a positive sign is needed.
     */
    val plusSignSuffix: String,
    /**
     * Character used to separate the integer and fraction parts of the number.
     */
    val decimalSeparator: String,
    /**
     * Character used to separate groups in the integer part of the number.
     */
    val groupingSeparator: String,
    /**
     * The numbering system to use.
     */
    val numsys: String,
) {
    /**
     * Build [DecimalSymbolStrs].
     */
    fun build(): DecimalSymbolStrs =
        DecimalSymbolStrs(
            minusSignPrefix = minusSignPrefix,
            minusSignSuffix = minusSignSuffix,
            plusSignPrefix = plusSignPrefix,
            plusSignSuffix = plusSignSuffix,
            decimalSeparator = decimalSeparator,
            groupingSeparator = groupingSeparator,
            numsys = numsys,
        )
}

/**
 * String data for the symbols: plus/minus affixes and separators.
 */
data class DecimalSymbolStrs(
    val minusSignPrefix: String,
    val minusSignSuffix: String,
    val plusSignPrefix: String,
    val plusSignSuffix: String,
    val decimalSeparator: String,
    val groupingSeparator: String,
    val numsys: String,
)

/**
 * Prefix and suffix to apply when a sign is needed.
 */
data class SignAffixes(
    val prefix: String,
    val suffix: String,
)

/**
 * Symbols and metadata required for formatting a decimal.
 *
 * This code is considered unstable; it may change at any time, in breaking or
 * non-breaking ways, including in SemVer minor releases. While the serialized
 * representation of data structs is guaranteed to be stable, their Kotlin
 * representation might not be. Use with caution.
 */
data class DecimalSymbols(
    /**
     * String data for the symbols: plus/minus affixes and separators.
     *
     * Upstream stores this as a packed variable-width buffer to reduce stack
     * size. Kotlin keeps the same semantic fields in a value object.
     */
    val strings: DecimalSymbolStrs,
    /**
     * Settings used to determine where to place groups in the integer part of
     * the number.
     */
    val groupingSizes: GroupingSizes,
) {
    /**
     * Return the prefix and suffix for the minus sign.
     */
    fun minusSignAffixes(): SignAffixes =
        SignAffixes(
            prefix = strings.minusSignPrefix,
            suffix = strings.minusSignSuffix,
        )

    /**
     * Return the prefix and suffix for the plus sign.
     */
    fun plusSignAffixes(): SignAffixes =
        SignAffixes(
            prefix = strings.plusSignPrefix,
            suffix = strings.plusSignSuffix,
        )

    /**
     * Return the decimal separator.
     */
    fun decimalSeparator(): String = strings.decimalSeparator

    /**
     * Return the grouping separator.
     */
    fun groupingSeparator(): String = strings.groupingSeparator

    /**
     * Return the numbering system.
     */
    fun numsys(): String = strings.numsys

    companion object {
        /**
         * Create a new en-US format for use in testing.
         */
        fun newEnForTesting(): DecimalSymbols {
            val strings =
                DecimalSymbolStrsBuilder(
                    minusSignPrefix = "-",
                    minusSignSuffix = "",
                    plusSignPrefix = "+",
                    plusSignSuffix = "",
                    decimalSeparator = ".",
                    groupingSeparator = ",",
                    numsys = "latn",
                )
            return DecimalSymbols(
                strings = strings.build(),
                groupingSizes =
                    GroupingSizes(
                        primary = 3u,
                        secondary = 3u,
                        minGrouping = 1u,
                    ),
            )
        }
    }
}
