package br.edu.utfpr.financeflow.viewmodel

import androidx.lifecycle.ViewModel
import br.edu.utfpr.financeflow.data.model.Transaction
import br.edu.utfpr.financeflow.data.model.TransactionType
import br.edu.utfpr.financeflow.data.repository.TransactionRepository
import br.edu.utfpr.financeflow.utils.BalanceCalculator
import br.edu.utfpr.financeflow.utils.MoneyFormatter
import br.edu.utfpr.financeflow.utils.TransactionValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TransactionViewModel(
    private val repository: TransactionRepository,
    private val now: () -> Long = System::currentTimeMillis
) : ViewModel() {

    private val _formState = MutableStateFlow(LaunchFormState())
    val formState: StateFlow<LaunchFormState> = _formState.asStateFlow()

    private val _statementState = MutableStateFlow(StatementUiState())
    val statementState: StateFlow<StatementUiState> = _statementState.asStateFlow()

    private val _message = MutableStateFlow<StatementMessage?>(null)
    val message: StateFlow<StatementMessage?> = _message.asStateFlow()

    init {
        loadTransactions()
    }

    fun onDescriptionChange(value: String) {
        _formState.update { it.copy(description = value, errors = it.errors.copy(description = null)) }
    }

    fun onAmountChange(value: String) {
        _formState.update { it.copy(amount = value, errors = it.errors.copy(amount = null)) }
    }

    fun onDateSelected(dateMillis: Long) {
        _formState.update { it.copy(dateMillis = dateMillis, errors = it.errors.copy(date = null)) }
    }

    fun onTypeChange(type: TransactionType) {
        _formState.update { it.copy(type = type) }
    }

    fun save(): Boolean {
        val current = _formState.value
        val errors = TransactionValidator.validate(
            description = current.description,
            amountText = current.amount,
            dateMillis = current.dateMillis
        )
        if (!errors.isValid) {
            _formState.update { it.copy(errors = errors) }
            return false
        }

        val transaction = Transaction(
            description = current.description.trim(),
            amount = MoneyFormatter.parse(current.amount) ?: return false,
            dateMillis = current.dateMillis ?: return false,
            type = current.type
        )
        repository.insert(transaction)
        _formState.value = LaunchFormState()
        loadTransactions()
        _message.value = StatementMessage.SAVED
        return true
    }

    fun deleteTransaction(id: Long) {
        repository.delete(id)
        loadTransactions()
        _message.value = StatementMessage.REMOVED
    }

    fun onMessageShown() {
        _message.value = null
    }

    fun loadTransactions() {
        val transactions = repository.findAll()
        _statementState.value = StatementUiState(
            transactions = transactions,
            balance = BalanceCalculator.balance(transactions),
            totalIncome = BalanceCalculator.totalIncome(transactions),
            totalExpense = BalanceCalculator.totalExpense(transactions),
            lastUpdatedMillis = now()
        )
    }
}
