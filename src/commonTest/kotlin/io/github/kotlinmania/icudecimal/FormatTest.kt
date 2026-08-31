// port-lint: tests format.rs
package io.github.kotlinmania.icudecimal

import io.github.kotlinmania.icudecimal.input.Decimal
import io.github.kotlinmania.icudecimal.options.DecimalFormatterOptions
import kotlin.test.Test
import kotlin.test.assertEquals

class FormatTest {
    @Test
    fun testEsMx() {
        val formatter = DecimalFormatter.tryNew("es-MX", DecimalFormatterOptions())
        val fd = Decimal.parse("12345.67")
        assertEquals("12,345.67", formatter.format(fd).toString())
    }
}
