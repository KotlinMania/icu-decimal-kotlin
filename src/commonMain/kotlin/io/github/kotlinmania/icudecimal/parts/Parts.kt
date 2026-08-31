// port-lint: source icu_decimal/src/parts.rs
package io.github.kotlinmania.icudecimal.parts

import io.github.kotlinmania.writeable.Part

/**
 * Parts of a formatted decimal.
 *
 * Part annotations identify the semantic range of substrings in formatted
 * output. A formatter for the value `-987654.321` in an English-style locale
 * can expose ranges like this:
 *
 * ```kotlin
 * val text = "-987,654.321"
 * val parts = listOf(
 *     0 until 1 to MINUS_SIGN,
 *     1 until 8 to INTEGER,
 *     4 until 5 to GROUP,
 *     8 until 9 to DECIMAL,
 *     9 until 12 to FRACTION,
 * )
 * ```
 *
 * The string remains useful on its own, while the ranges allow callers to
 * style or inspect signs, integer digits, grouping separators, decimal
 * separators, and fraction digits separately.
 */

/**
 * A [Part] used by formatted decimal output for positive signs.
 */
val PLUS_SIGN: Part =
    Part(
        category = "decimal",
        value = "plusSign",
    )

/**
 * A [Part] used by formatted decimal output for negative signs.
 */
val MINUS_SIGN: Part =
    Part(
        category = "decimal",
        value = "minusSign",
    )

/**
 * A [Part] used by formatted decimal output for integer digits.
 */
val INTEGER: Part =
    Part(
        category = "decimal",
        value = "integer",
    )

/**
 * A [Part] used by formatted decimal output for fraction digits.
 */
val FRACTION: Part =
    Part(
        category = "decimal",
        value = "fraction",
    )

/**
 * A [Part] used by formatted decimal output for grouping separators.
 */
val GROUP: Part =
    Part(
        category = "decimal",
        value = "group",
    )

/**
 * A [Part] used by formatted decimal output for decimal separators.
 */
val DECIMAL: Part =
    Part(
        category = "decimal",
        value = "decimal",
    )
