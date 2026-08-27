// port-lint: tests icu_decimal/src/options.rs
package io.github.kotlinmania.icudecimal.options

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class OptionsTest {
    @Test
    fun testOptionsDefaultsAndFactory() {
        val defaultOpts = DecimalFormatterOptions()
        assertNull(defaultOpts.groupingStrategy)

        val customOpts = DecimalFormatterOptions.from(GroupingStrategy.Min2)
        assertEquals(GroupingStrategy.Min2, customOpts.groupingStrategy)

        val entries = GroupingStrategy.entries
        assertEquals(4, entries.size)
    }
}
