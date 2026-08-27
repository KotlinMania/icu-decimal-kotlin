// port-lint: tests icu_decimal/benches/fixed_decimal_format.rs
package io.github.kotlinmania.icudecimal

import io.github.kotlinmania.icudecimal.input.Decimal
import io.github.kotlinmania.icudecimal.options.DecimalFormatterOptions
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FixedDecimalFormatBenchTest {
    private fun triangularNums(range: Double): List<Long> {
        val random = Random(2020)
        val nums = mutableListOf<Long>()
        for (i in 0 until 1000) {
            // Approximation of triangular distribution around 0
            val u1 = random.nextDouble()
            val u2 = random.nextDouble()
            val v = (u1 - u2) * range
            nums.add(v.toLong())
        }
        return nums
    }

    private fun overviewBench() {
        val nums = triangularNums(1e9)
        val formatter = DecimalFormatter.tryNew("en-US", DecimalFormatterOptions())
        for (num in nums) {
            val decimal = Decimal.from(num)
            val str = formatter.formatToString(decimal)
            assertTrue(str.isNotEmpty())
        }
    }

    @Test
    fun testTriangularNums() {
        val nums = triangularNums(1e9)
        assertEquals(1000, nums.size)
    }

    @Test
    fun testOverviewBench() {
        overviewBench()
    }
}
