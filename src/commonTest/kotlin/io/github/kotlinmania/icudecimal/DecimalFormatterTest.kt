// port-lint: tests lib.rs
package io.github.kotlinmania.icudecimal

import io.github.kotlinmania.icudecimal.input.Decimal
import io.github.kotlinmania.icudecimal.options.DecimalFormatterOptions
import io.github.kotlinmania.icudecimal.options.GroupingStrategy
import io.github.kotlinmania.icudecimal.parts.DECIMAL
import io.github.kotlinmania.icudecimal.parts.FRACTION
import io.github.kotlinmania.icudecimal.parts.GROUP
import io.github.kotlinmania.icudecimal.parts.INTEGER
import io.github.kotlinmania.icudecimal.parts.MINUS_SIGN
import kotlin.math.pow
import kotlin.math.round
import kotlin.test.Test
import kotlin.test.assertEquals

class DecimalFormatterTest {
    @Test
    fun formatsGroupedInteger() {
        val formatter = DecimalFormatter.tryNew("en-US", DecimalFormatterOptions())

        assertEquals("0", formatter.format(Decimal.from(0)).toString())
        assertEquals("999", formatter.format(Decimal.from(999)).toString())
        assertEquals("1,000", formatter.format(Decimal.from(1_000)).toString())
        assertEquals("1,234,567", formatter.format(Decimal.from(1_234_567)).toString())
        assertEquals("-1,234,567", formatter.format(Decimal.from(-1_234_567)).toString())
    }

    @Test
    fun formatsFractionalMagnitudeShift() {
        val formatter = DecimalFormatter.tryNew("en-US", DecimalFormatterOptions())
        val decimal = Decimal.from(200_050)

        decimal.multiplyPow10(-2)

        assertEquals("2,000.50", formatter.format(decimal).toString())
    }

    @Test
    fun formatsParsedDecimalLikeUpstreamFormatTest() {
        val formatter = DecimalFormatter.tryNew("es-MX", DecimalFormatterOptions())

        assertEquals("12,345.67", formatter.format(Decimal.parse("12345.67")).toString())
    }

    @Test
    fun formatsDocumentedNumberingSystems() {
        assertEquals(
            "\u09E7\u09E6,\u09E6\u09E6,\u09E6\u09E6\u09ED",
            DecimalFormatter
                .tryNew("bn", DecimalFormatterOptions())
                .format(Decimal.from(1_000_007))
                .toString(),
        )
        assertEquals(
            "\u0E51,\u0E50\u0E50\u0E50,\u0E50\u0E50\u0E57",
            DecimalFormatter
                .tryNew("th-u-nu-thai", DecimalFormatterOptions())
                .format(Decimal.from(1_000_007))
                .toString(),
        )
    }

    @Test
    fun formatsNumberingResolutionFallbackCases() {
        fun testLocale(locale: String, expected: String) {
            val formatter = DecimalFormatter.tryNew(locale, DecimalFormatterOptions())
            assertEquals(expected, formatter.format(Decimal.from(1234)).toString(), locale)
        }

        testLocale("en", "1,234")
        testLocale("en-u-nu-arab", "\u0661,\u0662\u0663\u0664")
        testLocale("ar-EG", "\u0661\u066C\u0662\u0663\u0664")
        testLocale("ar-EG-u-nu-latn", "1,234")
        testLocale("ar-EG-u-nu-thai", "\u0E51\u066C\u0E52\u0E53\u0E54")
        testLocale("en-u-nu-wxyz", "1,234")
        testLocale("ar-EG-u-nu-wxyz", "\u0661\u066C\u0662\u0663\u0664")
    }

    @Test
    fun testNumberingResolutionFallback() {
        formatsNumberingResolutionFallbackCases()
    }

    @Test
    fun honorsGroupingStrategy() {
        val formatter =
            DecimalFormatter.tryNew(
                locale = "en-US",
                options = DecimalFormatterOptions.from(GroupingStrategy.Min2),
            )

        assertEquals("1000", formatter.format(Decimal.from(1_000)).toString())
        assertEquals("10,000", formatter.format(Decimal.from(10_000)).toString())
    }

    @Test
    fun exposesFormattedParts() {
        val formatter = DecimalFormatter.tryNew("en-US", DecimalFormatterOptions())
        val decimal = Decimal.parse("-12345.67")

        val formatted = formatter.format(decimal)

        assertEquals("-12,345.67", formatted.toString())
        assertEquals(
            listOf(
                MINUS_SIGN to 0..1,
                INTEGER to 1..2,
                INTEGER to 2..3,
                GROUP to 3..4,
                INTEGER to 4..5,
                INTEGER to 5..6,
                INTEGER to 6..7,
                DECIMAL to 7..8,
                FRACTION to 8..9,
                FRACTION to 9..10,
            ),
            formatted.toParts().map { it.part to it.start..it.end },
        )
    }

    @Test
    fun supportsCodexSiSuffixFormatting() {
        val formatter = DecimalFormatter.tryNew("en-US", DecimalFormatterOptions())
        val fmt = { value: Long -> formatSiSuffixWithFormatter(value, formatter) }

        assertEquals("0", fmt(0))
        assertEquals("999", fmt(999))
        assertEquals("1.00K", fmt(1_000))
        assertEquals("1.20K", fmt(1_200))
        assertEquals("10.0K", fmt(10_000))
        assertEquals("100K", fmt(100_000))
        assertEquals("1.00M", fmt(999_500))
        assertEquals("1.00M", fmt(1_000_000))
        assertEquals("1.23M", fmt(1_234_000))
        assertEquals("12.3M", fmt(12_345_678))
        assertEquals("1.00G", fmt(999_950_000))
        assertEquals("1.00G", fmt(1_000_000_000))
        assertEquals("1.23G", fmt(1_234_000_000))
        assertEquals("1,234G", fmt(1_234_000_000_000))
    }

    private fun formatSiSuffixWithFormatter(n: Long, formatter: DecimalFormatter): String {
        val value = n.coerceAtLeast(0)
        if (value < 1000) {
            return formatter.format(Decimal.from(value)).toString()
        }

        fun formatScaled(n: Long, scale: Long, fractionDigits: Int): String {
            val scaled =
                round((n.toDouble() / scale.toDouble()) * 10.0.pow(fractionDigits))
                    .toLong()
            val decimal = Decimal.from(scaled)
            decimal.multiplyPow10(-fractionDigits)
            return formatter.format(decimal).toString()
        }

        val units =
            listOf(
                1_000L to "K",
                1_000_000L to "M",
                1_000_000_000L to "G",
            )
        val floating = value.toDouble()
        for ((scale, suffix) in units) {
            if (round(100.0 * floating / scale.toDouble()) < 1000.0) {
                return formatScaled(value, scale, 2) + suffix
            }
            if (round(10.0 * floating / scale.toDouble()) < 1000.0) {
                return formatScaled(value, scale, 1) + suffix
            }
            if (round(floating / scale.toDouble()) < 1000.0) {
                return formatScaled(value, scale, 0) + suffix
            }
        }

        return formatter.format(Decimal.from(round(floating / 1e9).toLong())).toString() + "G"
    }

    @Test
    fun testDecimalFormatterPreferences() {
        val prefs = DecimalFormatterPreferences(locale = "th", numberingSystem = "thai")
        val formatter = DecimalFormatter.tryNew(prefs)
        val decimal = Decimal.from(1_000_007)
        assertEquals("\u0E51,\u0E50\u0E50\u0E50,\u0E50\u0E50\u0E57", formatter.format(decimal).toString())
    }

    @Test
    fun testBenchOverviewLoop() {
        val prefs = DecimalFormatterPreferences.from("en-US")
        val formatter = DecimalFormatter.tryNew(prefs, DecimalFormatterOptions())
        val testNums = longArrayOf(0L, 1L, -1L, 1000L, 1_234_567L, -9_876_543L)
        for (num in testNums) {
            val decimal = Decimal.from(num)
            val str = formatter.formatToString(decimal)
            assertEquals(formatter.format(decimal).toString(), str)
        }
    }
}
