package br.edu.utfpr.financeflow.viewmodel

import br.edu.utfpr.financeflow.data.model.TransactionType
import br.edu.utfpr.financeflow.utils.TransactionFormErrors

data class LaunchFormState(
    val description: String = "",
    val amount: String = "",
    val dateMillis: Long? = null,
    val type: TransactionType = TransactionType.INCOME,
    val errors: TransactionFormErrors = TransactionFormErrors()
)
