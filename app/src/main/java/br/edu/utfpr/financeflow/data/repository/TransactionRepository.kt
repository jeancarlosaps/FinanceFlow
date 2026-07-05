package br.edu.utfpr.financeflow.data.repository

import br.edu.utfpr.financeflow.data.model.Transaction

interface TransactionRepository {
    fun insert(transaction: Transaction): Long
    fun findAll(): List<Transaction>
}
