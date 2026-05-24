// port-lint: source provider.rs
package io.github.kotlinmania.icudecimal.provider

import kotlin.test.Test
import kotlin.test.assertEquals

class ProviderTest {
    @Test
    fun decimalSymbolsExposeSignAffixes() {
        val symbols = DecimalSymbols.newEnForTesting()

        assertEquals(SignAffixes(prefix = "-", suffix = ""), symbols.minusSignAffixes())
        assertEquals(SignAffixes(prefix = "+", suffix = ""), symbols.plusSignAffixes())
    }
}
