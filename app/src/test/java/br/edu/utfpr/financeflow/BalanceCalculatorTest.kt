package br.edu.utfpr.financeflow

import br.edu.utfpr.financeflow.data.model.Transaction
import br.edu.utfpr.financeflow.data.model.TransactionType
import br.edu.utfpr.financeflow.utils.BalanceCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

class BalanceCalculatorTest {

    private fun income(amount: Double) =
        Transaction(description = "in", amount = amount, dateMillis = 0L, type = TransactionType.INCOME)

    private fun expense(amount: Double) =
        Transaction(description = "out", amount = amount, dateMillis = 0L, type = TransactionType.EXPENSE)

    @Test
    fun `balance of empty list is zero`() {
        assertEquals(0.0, BalanceCalculator.balance(emptyList()), 0.001)
    }

    @Test
    fun `income adds and expense subtracts`() {
        val transactions = listOf(income(1000.0), expense(300.0), income(200.0))
        assertEquals(900.0, BalanceCalculator.balance(transactions), 0.001)
    }

    @Test
    fun `balance can be negative`() {
        val transactions = listOf(income(100.0), expense(250.0))
        assertEquals(-150.0, BalanceCalculator.balance(transactions), 0.001)
    }

    @Test
    fun `total income sums only incomes`() {
        val transactions = listOf(income(1000.0), expense(300.0), income(200.0))
        assertEquals(1200.0, BalanceCalculator.totalIncome(transactions), 0.001)
    }

    @Test
    fun `total expense sums only expenses`() {
        val transactions = listOf(income(1000.0), expense(300.0), expense(50.0))
        assertEquals(350.0, BalanceCalculator.totalExpense(transactions), 0.001)
    }
}
