// port-lint: source src/grouper.rs
package io.github.kotlinmania.icudecimal.grouper

import io.github.kotlinmania.icudecimal.options.GroupingStrategy
import io.github.kotlinmania.icudecimal.provider.GroupingSizes

/**
 * Algorithms to determine where to position grouping separators.
 */

/**
 * Returns whether to display a grouping separator at the given magnitude.
 *
 * [upperMagnitude] is the magnitude of the highest-power digit, used for
 * resolving minimum grouping digits.
 */
internal fun check(
    upperMagnitude: Short,
    magnitude: Short,
    strategy: GroupingStrategy,
    sizes: GroupingSizes,
): Boolean {
    val primary = if (sizes.primary == UByte.MIN_VALUE) {
        return false
    } else {
        sizes.primary.toInt()
    }
    if (magnitude.toInt() < primary) {
        return false
    }
    val minGrouping = when (strategy) {
        GroupingStrategy.Never -> return false
        // Auto and Always are the same for DecimalFormatter. When currencies
        // are implemented, this will change.
        GroupingStrategy.Auto,
        GroupingStrategy.Always,
        -> maxOf(1, sizes.minGrouping.toInt())
        GroupingStrategy.Min2 -> maxOf(2, sizes.minGrouping.toInt())
    }
    if (upperMagnitude.toInt() < primary + minGrouping - 1) {
        return false
    }
    val secondary = if (sizes.secondary == UByte.MIN_VALUE) {
        primary
    } else {
        sizes.secondary.toInt()
    }
    val magnitudePrime = magnitude.toInt() - primary
    return magnitudePrime % secondary == 0
}
