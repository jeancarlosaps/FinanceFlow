package br.edu.utfpr.financeflow.utils

import br.edu.utfpr.financeflow.data.model.Transaction
import br.edu.utfpr.financeflow.data.model.TransactionType

object BalanceCalculator {

    fun balance(transactions: List<Transaction>): Double =
        transactions.sumOf { it.signedAmount }

    fun totalIncome(transactions: List<Transaction>): Double =
        transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }

    fun totalExpense(transactions: List<Transaction>): Double =
        transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
}
