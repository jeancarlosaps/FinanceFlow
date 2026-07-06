package br.edu.utfpr.financeflow

import br.edu.utfpr.financeflow.data.model.Transaction
import br.edu.utfpr.financeflow.data.model.TransactionType
import br.edu.utfpr.financeflow.viewmodel.StatementMessage
import br.edu.utfpr.financeflow.viewmodel.TransactionViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TransactionViewModelTest {

    private fun viewModel(vararg initial: Transaction) =
        TransactionViewModel(FakeTransactionRepository(initial.toList()), now = { fixedNow })

    private val date = 1_700_000_000_000L
    private val fixedNow = 1_720_000_000_000L

    @Test
    fun `loads existing transactions and balance on init`() {
        val vm = viewModel(
            Transaction(description = "Salário", amount = 1000.0, dateMillis = date, type = TransactionType.INCOME),
            Transaction(description = "Aluguel", amount = 400.0, dateMillis = date, type = TransactionType.EXPENSE)
        )
        val state = vm.statementState.value
        assertEquals(2, state.transactions.size)
        assertEquals(600.0, state.balance, 0.001)
    }

    @Test
    fun `field changes update form state`() {
        val vm = viewModel()
        vm.onDescriptionChange("Mercado")
        vm.onAmountChange("150,00")
        vm.onDateSelected(date)
        vm.onTypeChange(TransactionType.EXPENSE)

        val form = vm.formState.value
        assertEquals("Mercado", form.description)
        assertEquals("150,00", form.amount)
        assertEquals(date, form.dateMillis)
        assertEquals(TransactionType.EXPENSE, form.type)
    }

    @Test
    fun `save with invalid input fails and reports errors`() {
        val vm = viewModel()
        vm.onDescriptionChange("")
        vm.onAmountChange("0")

        val saved = vm.save()

        assertFalse(saved)
        assertNotNull(vm.formState.value.errors.description)
        assertNotNull(vm.formState.value.errors.amount)
        assertTrue(vm.statementState.value.transactions.isEmpty())
    }

    @Test
    fun `save with valid input persists, resets form and updates balance`() {
        val vm = viewModel()
        vm.onDescriptionChange("Salário")
        vm.onAmountChange("2500,00")
        vm.onDateSelected(date)
        vm.onTypeChange(TransactionType.INCOME)

        val saved = vm.save()

        assertTrue(saved)
        assertEquals(1, vm.statementState.value.transactions.size)
        assertEquals(2500.0, vm.statementState.value.balance, 0.001)
        assertEquals("", vm.formState.value.description)
        assertEquals("", vm.formState.value.amount)
        assertNull(vm.formState.value.dateMillis)
    }

    @Test
    fun `successful save emits saved message that can be consumed`() {
        val vm = viewModel()
        vm.onDescriptionChange("Salário")
        vm.onAmountChange("2500,00")
        vm.onDateSelected(date)

        vm.save()
        assertEquals(StatementMessage.SAVED, vm.message.value)

        vm.onMessageShown()
        assertNull(vm.message.value)
    }

    @Test
    fun `delete removes transaction, updates balance and emits removed message`() {
        val vm = viewModel(
            Transaction(id = 1L, description = "Salário", amount = 1000.0, dateMillis = date, type = TransactionType.INCOME),
            Transaction(id = 2L, description = "Aluguel", amount = 400.0, dateMillis = date, type = TransactionType.EXPENSE)
        )

        vm.deleteTransaction(2L)

        assertEquals(1, vm.statementState.value.transactions.size)
        assertEquals(1000.0, vm.statementState.value.balance, 0.001)
        assertEquals(StatementMessage.REMOVED, vm.message.value)
    }

    @Test
    fun `statement records last updated timestamp from clock`() {
        val vm = viewModel()
        assertEquals(fixedNow, vm.statementState.value.lastUpdatedMillis)
    }
}
