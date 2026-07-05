package br.edu.utfpr.financeflow

import br.edu.utfpr.financeflow.utils.TransactionValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TransactionValidatorTest {

    private val validDate = 1_700_000_000_000L

    @Test
    fun `valid input produces no errors`() {
        val errors = TransactionValidator.validate("Salário", "2500,00", validDate)
        assertTrue(errors.isValid)
    }

    @Test
    fun `blank description is rejected`() {
        val errors = TransactionValidator.validate("   ", "10,00", validDate)
        assertNotNull(errors.description)
        assertFalse(errors.isValid)
    }

    @Test
    fun `blank amount is rejected`() {
        val errors = TransactionValidator.validate("Aluguel", "", validDate)
        assertNotNull(errors.amount)
    }

    @Test
    fun `zero amount is rejected`() {
        val errors = TransactionValidator.validate("Aluguel", "0", validDate)
        assertNotNull(errors.amount)
    }

    @Test
    fun `negative amount is rejected`() {
        val errors = TransactionValidator.validate("Aluguel", "-5,00", validDate)
        assertNotNull(errors.amount)
    }

    @Test
    fun `missing date is rejected`() {
        val errors = TransactionValidator.validate("Aluguel", "10,00", null)
        assertNotNull(errors.date)
    }

    @Test
    fun `valid positive amount passes amount rule`() {
        val errors = TransactionValidator.validate("Aluguel", "10,00", validDate)
        assertNull(errors.amount)
    }
}
