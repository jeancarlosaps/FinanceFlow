package br.edu.utfpr.financeflow

import br.edu.utfpr.financeflow.utils.MoneyFormatter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MoneyFormatterTest {

    @Test
    fun `parse accepts comma as decimal separator`() {
        assertEquals(12.5, MoneyFormatter.parse("12,50")!!, 0.001)
    }

    @Test
    fun `parse accepts dot as decimal separator`() {
        assertEquals(12.5, MoneyFormatter.parse("12.50")!!, 0.001)
    }

    @Test
    fun `parse ignores currency symbol and thousands separator`() {
        assertEquals(1234.56, MoneyFormatter.parse("R$ 1.234,56")!!, 0.001)
    }

    @Test
    fun `parse returns null for blank input`() {
        assertNull(MoneyFormatter.parse("   "))
    }

    @Test
    fun `parse returns null for non numeric input`() {
        assertNull(MoneyFormatter.parse("abc"))
    }

    @Test
    fun `format and parse round trip preserves value`() {
        val formatted = MoneyFormatter.format(1234.56)
        assertEquals(1234.56, MoneyFormatter.parse(formatted)!!, 0.001)
    }
}
