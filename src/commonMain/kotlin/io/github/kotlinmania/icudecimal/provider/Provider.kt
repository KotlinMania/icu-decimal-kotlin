// port-lint: source src/provider.rs
package io.github.kotlinmania.icudecimal.provider

/**
 * A collection of settings expressing where to put grouping separators in a
 * decimal number. For example, `1,000,000` has two grouping separators,
 * positioned along every 3 digits.
 */
data class GroupingSizes(
    /**
     * The size of the first, lowest-magnitude group.
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
