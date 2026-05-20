// port-lint: source src/options.rs
package io.github.kotlinmania.icudecimal.options

/**
 * Options for [io.github.kotlinmania.icudecimal.DecimalFormatter].
 */

/**
 * A bag of options defining how numbers will be formatted by
 * [io.github.kotlinmania.icudecimal.DecimalFormatter].
 */
data class DecimalFormatterOptions(
    /**
     * When to render grouping separators.
     *
     * Default is [GroupingStrategy.Auto].
     */
    val groupingStrategy: GroupingStrategy? = null,
) {
    companion object {
        fun from(groupingStrategy: GroupingStrategy): DecimalFormatterOptions =
            DecimalFormatterOptions(groupingStrategy = groupingStrategy)
    }
}

/**
 * Configuration for how often to render grouping separators.
 *
 * Example:
 *
 * ```kotlin
 * val locale = Locale.Default
 * val options = DecimalFormatterOptions(
 *     groupingStrategy = GroupingStrategy.Min2,
 * )
 * val formatter = DecimalFormatter.tryNew(locale, options)
 *     .getOrThrow()
 *
 * val oneThousand = Decimal.from(1000)
 * check(formatter.format(oneThousand).toString() == "1000")
 *
 * val tenThousand = Decimal.from(10000)
 * check(formatter.format(tenThousand).toString() == "10,000")
 * ```
 */
enum class GroupingStrategy {
    /**
     * Render grouping separators according to locale preferences.
     */
    Auto,

    /**
     * Never render grouping separators.
     */
    Never,

    /**
     * Always render grouping separators.
     *
     * For [io.github.kotlinmania.icudecimal.DecimalFormatter], [Always] has
     * the same behavior as [Auto].
     */
    Always,

    /**
     * Render grouping separators only if there are at least 2 digits before
     * the final grouping separator. In most locales, this means that numbers
     * between 1000 and 9999 do not get grouping separators, but numbers 10,000
     * and above will.
     */
    Min2,
}
