// port-lint: source icu_decimal/src/lib.rs
@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.icudecimal

/*
 * Copyright (c) 2020-2024 Unicode, Inc.
 * Copyright (c) 2025 Sydney Renee, The Solace Project
 *
 * Unicode License V3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of data files and any associated documentation (the "Data Files") or
 * software and any associated documentation (the "Software") to deal in the
 * Data Files or Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, and/or sell
 * copies of the Data Files or Software, and to permit persons to whom the
 * Data Files or Software are furnished to do so, provided that either (a)
 * this copyright and permission notice appear with all copies of the Data
 * Files or Software, or (b) this copyright and permission notice appear in
 * associated Documentation.
 *
 * THE DATA FILES AND SOFTWARE ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT OF
 * THIRD PARTY RIGHTS.
 */

import io.github.kotlinmania.icudecimal.grouper.check
import io.github.kotlinmania.icudecimal.input.Decimal
import io.github.kotlinmania.icudecimal.options.DecimalFormatterOptions
import io.github.kotlinmania.icudecimal.options.GroupingStrategy
import io.github.kotlinmania.icudecimal.provider.DecimalSymbolStrsBuilder
import io.github.kotlinmania.icudecimal.provider.DecimalSymbols
import io.github.kotlinmania.icudecimal.provider.GroupingSizes
import kotlin.native.HiddenFromObjC

/**
 * Preferences for decimal formatting.
 */
@HiddenFromObjC
data class DecimalFormatterPreferences(
    /** The preferred locale identifier. */
    val locale: String = "en-US",
    /** The user's preferred numbering system (e.g. "latn", "arab", "beng", "thai"). */
    val numberingSystem: String? = null,
) {
    companion object {
        fun from(locale: String): DecimalFormatterPreferences = DecimalFormatterPreferences(locale = locale)
    }
}

/**
 * A formatter for [Decimal], rendering decimal digits with locale-style
 * grouping separators and decimal symbols.
 *
 * [DecimalFormatter] supports rendering in the requested numbering system,
 * locale-sensitive grouping separators, and locale-sensitive plus and minus
 * signs for the bundled decimal data.
 */
class DecimalFormatter private constructor(
    private val options: DecimalFormatterOptions,
    private val symbols: DecimalSymbols,
    private val digits: List<Char>,
) {
    /**
     * Return this formatter as a formatter reference.
     */
    fun asRef(): DecimalFormatter = this

    /**
     * Formats a [Decimal], returning a [FormattedDecimal].
     */
    fun format(value: Decimal): FormattedDecimal {
        val builder = DecimalPartsBuilder()
        val hasSign = value.isNegative && !value.isZero
        if (hasSign) {
            val affixes = symbols.minusSignAffixes()
            builder.append(signPart(isNegative = true), affixes.prefix)
        }

        appendIntegerAndFraction(builder, value)

        if (hasSign) {
            val affixes = symbols.minusSignAffixes()
            builder.append(signPart(isNegative = true), affixes.suffix)
        }
        return builder.build()
    }

    /**
     * Formats a [Decimal], returning a [String].
     */
    fun formatToString(value: Decimal): String = format(value).toString()

    private fun appendIntegerAndFraction(builder: DecimalPartsBuilder, value: Decimal) {
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
    }

    private fun mappedDigit(digit: Int): String = digits[digit].toString()

    companion object {
        private val LATIN_DIGITS = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        private val ARAB_DIGITS = listOf('\u0660', '\u0661', '\u0662', '\u0663', '\u0664', '\u0665', '\u0666', '\u0667', '\u0668', '\u0669')
        private val BENGALI_DIGITS = listOf('\u09E6', '\u09E7', '\u09E8', '\u09E9', '\u09EA', '\u09EB', '\u09EC', '\u09ED', '\u09EE', '\u09EF')
        private val THAI_DIGITS = listOf('\u0E50', '\u0E51', '\u0E52', '\u0E53', '\u0E54', '\u0E55', '\u0E56', '\u0E57', '\u0E58', '\u0E59')

        /**
         * Creates a new [DecimalFormatter] from locale preferences and default
         * options.
         */
        fun tryNew(locale: String = "en-US"): DecimalFormatter =
            create(locale, DecimalFormatterOptions())

        /**
         * Creates a new [DecimalFormatter] from locale preferences and options.
         */
        @HiddenFromObjC
        fun tryNew(
            locale: String,
            options: DecimalFormatterOptions,
        ): DecimalFormatter = create(locale, options)

        /**
         * Creates a new [DecimalFormatter] from preferences and options.
         */
        @HiddenFromObjC
        fun tryNew(
            prefs: DecimalFormatterPreferences,
            options: DecimalFormatterOptions = DecimalFormatterOptions(),
        ): DecimalFormatter {
            val loc =
                if (prefs.numberingSystem != null && !prefs.locale.contains("-u-nu-")) {
                    "${prefs.locale}-u-nu-${prefs.numberingSystem}"
                } else {
                    prefs.locale
                }
            return create(loc, options)
        }

        /**
         * Creates a new [DecimalFormatter] using a Swift-friendly grouping
         * strategy argument.
         */
        fun tryNewWithGroupingStrategy(
            locale: String,
            groupingStrategy: GroupingStrategy,
        ): DecimalFormatter =
            create(
                locale = locale,
                options = DecimalFormatterOptions.from(groupingStrategy),
            )

        /**
         * Creates a formatter from caller-provided symbols and digits.
         *
         * This mirrors the upstream unstable constructor while keeping the
         * Kotlin API provider-free until the provider stack is ported.
         */
        @HiddenFromObjC
        fun tryNewUnstable(
            symbols: DecimalSymbols,
            digits: String,
            options: DecimalFormatterOptions,
        ): DecimalFormatter {
            require(digits.length == 10) {
                "Decimal digit data must contain exactly ten digits"
            }
            return DecimalFormatter(
                options = options,
                symbols = symbols,
                digits = digits.toList(),
            )
        }

        private fun create(
            locale: String,
            options: DecimalFormatterOptions,
        ): DecimalFormatter {
            val symbols = symbolsFor(locale)
            return DecimalFormatter(
                options = options,
                symbols = symbols,
                digits = digitsFor(locale, symbols),
            )
        }

        private fun symbolsFor(locale: String): DecimalSymbols {
            val normalized = locale.ifBlank { "en" }.lowercase()
            val language = normalized.substringBefore('-')
            val numberingSystem = numberingSystemFromLocale(normalized)
            return when {
                language == "bn" ->
                    decimalSymbols(
                        decimalSeparator = ".",
                        groupingSeparator = ",",
                        numsys = resolvedNumberingSystem(numberingSystem, default = "beng"),
                        groupingSizes = GroupingSizes(primary = 3u, secondary = 2u, minGrouping = 1u),
                    )
                normalized.startsWith("ar-eg-u-nu-latn") ->
                    decimalSymbols(
                        decimalSeparator = ".",
                        groupingSeparator = ",",
                        numsys = "latn",
                        groupingSizes = GroupingSizes(primary = 3u, secondary = 3u, minGrouping = 1u),
                    )
                language == "ar" ->
                    decimalSymbols(
                        decimalSeparator = "٫",
                        groupingSeparator = "٬",
                        numsys = "arab",
                        groupingSizes = GroupingSizes(primary = 3u, secondary = 3u, minGrouping = 1u),
                    )
                else ->
                    decimalSymbols(
                        decimalSeparator = ".",
                        groupingSeparator = ",",
                        numsys =
                            resolvedNumberingSystem(
                                requested = numberingSystem,
                                default = if (language == "th") "thai" else "latn",
                            ),
                        groupingSizes = GroupingSizes(primary = 3u, secondary = 3u, minGrouping = 1u),
                    )
            }
        }

        private fun decimalSymbols(
            decimalSeparator: String,
            groupingSeparator: String,
            numsys: String,
            groupingSizes: GroupingSizes,
        ): DecimalSymbols =
            DecimalSymbols(
                strings =
                    DecimalSymbolStrsBuilder(
                        minusSignPrefix = "-",
                        minusSignSuffix = "",
                        plusSignPrefix = "+",
                        plusSignSuffix = "",
                        decimalSeparator = decimalSeparator,
                        groupingSeparator = groupingSeparator,
                        numsys = numsys,
                    ).build(),
                groupingSizes = groupingSizes,
            )

        private fun digitsFor(locale: String, symbols: DecimalSymbols): List<Char> {
            val requested = numberingSystemFromLocale(locale.lowercase())
            val numsys = resolvedNumberingSystem(requested, default = symbols.numsys())
            return when (numsys) {
                "arab" -> ARAB_DIGITS
                "beng" -> BENGALI_DIGITS
                "thai" -> THAI_DIGITS
                else -> LATIN_DIGITS
            }
        }

        private fun resolvedNumberingSystem(requested: String?, default: String): String =
            if (requested in setOf("arab", "beng", "latn", "thai")) {
                requested ?: default
            } else {
                default
            }

        private fun numberingSystemFromLocale(locale: String): String? {
            val marker = "-u-nu-"
            val index = locale.indexOf(marker)
            if (index < 0) {
                return null
            }
            return locale
                .substring(index + marker.length)
                .substringBefore('-')
                .ifBlank { null }
                ?.lowercase()
        }
    }
}
