package br.edu.utfpr.financeflow.viewmodel

import br.edu.utfpr.financeflow.data.model.Transaction

data class StatementUiState(
    val transactions: List<Transaction> = emptyList(),
    val balance: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0
)
