// port-lint: tests icu_decimal/src/parts.rs
package io.github.kotlinmania.icudecimal.parts

import io.github.kotlinmania.writeable.Part
import kotlin.test.Test
import kotlin.test.assertEquals

class PartsTest {
    @Test
    fun decimalPartsExposeStableValues() {
        val cases =
            listOf(
                PLUS_SIGN to "plusSign",
                MINUS_SIGN to "minusSign",
                INTEGER to "integer",
                FRACTION to "fraction",
                GROUP to "group",
                DECIMAL to "decimal",
            )

        for ((part, value) in cases) {
            assertEquals("decimal", part.category)
            assertEquals(value, part.value)
        }
    }

    @Test
    fun partUsesValueEquality() {
        assertEquals(
            expected = Part(category = "decimal", value = "integer"),
            actual = INTEGER,
        )
    }
}
