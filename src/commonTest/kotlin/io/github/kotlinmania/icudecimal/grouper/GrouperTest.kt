// port-lint: source grouper.rs
package io.github.kotlinmania.icudecimal.grouper

import io.github.kotlinmania.icudecimal.options.GroupingStrategy
import io.github.kotlinmania.icudecimal.provider.GroupingSizes
import kotlin.test.Test
import kotlin.test.assertEquals

class GrouperTest {
    @Test
    fun testGrouper() {
        val westernSizes = GroupingSizes(
            minGrouping = 1u,
            primary = 3u,
            secondary = 3u,
        )
        val indicSizes = GroupingSizes(
            minGrouping = 1u,
            primary = 3u,
            secondary = 2u,
        )
        val westernSizesMin3 = GroupingSizes(
            minGrouping = 3u,
            primary = 3u,
            secondary = 3u,
        )

        // primary=0 implies no grouping; the other fields are ignored
        val zeroTest = GroupingSizes(
            minGrouping = 0u,
            primary = 0u,
            secondary = 0u,
        )

        // secondary=0 implies that it inherits from primary
        val blankSecondary = GroupingSizes(
            minGrouping = 0u,
            primary = 3u,
            secondary = 0u,
        )

        data class TestCase(
            val strategy: GroupingStrategy,
            val sizes: GroupingSizes,
            val expected: List<String>,
        )

        val cases = listOf(
            TestCase(
                strategy = GroupingStrategy.Auto,
                sizes = westernSizes,
                expected = listOf("1,000", "10,000", "100,000", "1,000,000"),
            ),
            TestCase(
                strategy = GroupingStrategy.Min2,
                sizes = westernSizes,
                expected = listOf("1000", "10,000", "100,000", "1,000,000"),
            ),
            TestCase(
                strategy = GroupingStrategy.Auto,
                sizes = indicSizes,
                expected = listOf("1,000", "10,000", "1,00,000", "10,00,000"),
            ),
            TestCase(
                strategy = GroupingStrategy.Min2,
                sizes = indicSizes,
                expected = listOf("1000", "10,000", "1,00,000", "10,00,000"),
            ),
            TestCase(
                strategy = GroupingStrategy.Auto,
                sizes = westernSizesMin3,
                expected = listOf("1000", "10000", "100,000", "1,000,000"),
            ),
            TestCase(
                strategy = GroupingStrategy.Min2,
                sizes = westernSizesMin3,
                expected = listOf("1000", "10000", "100,000", "1,000,000"),
            ),
            TestCase(
                strategy = GroupingStrategy.Auto,
                sizes = zeroTest,
                expected = listOf("1000", "10000", "100000", "1000000"),
            ),
            TestCase(
                strategy = GroupingStrategy.Min2,
                sizes = zeroTest,
                expected = listOf("1000", "10000", "100000", "1000000"),
            ),
            TestCase(
                strategy = GroupingStrategy.Auto,
                sizes = blankSecondary,
                expected = listOf("1,000", "10,000", "100,000", "1,000,000"),
            ),
            TestCase(
                strategy = GroupingStrategy.Min2,
                sizes = blankSecondary,
                expected = listOf("1000", "10,000", "100,000", "1,000,000"),
            ),
        )

        for (case in cases) {
            for (index in 0..3) {
                val upperMagnitude = (index + 3).toShort()
                val actual = renderOneFollowedByZeros(
                    upperMagnitude = upperMagnitude,
                    strategy = case.strategy,
                    sizes = case.sizes,
                )
                assertEquals(case.expected[index], actual, case.toString())
            }
        }
    }

    private fun renderOneFollowedByZeros(
        upperMagnitude: Short,
        strategy: GroupingStrategy,
        sizes: GroupingSizes,
    ): String = buildString {
        for (magnitude in upperMagnitude.toInt() downTo 0) {
            append(if (magnitude == upperMagnitude.toInt()) '1' else '0')
            if (
                check(
                    upperMagnitude = upperMagnitude,
                    magnitude = magnitude.toShort(),
                    strategy = strategy,
                    sizes = sizes,
                )
            ) {
                append(',')
            }
        }
    }
}
