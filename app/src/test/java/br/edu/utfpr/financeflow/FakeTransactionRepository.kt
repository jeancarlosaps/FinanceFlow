package br.edu.utfpr.financeflow

import br.edu.utfpr.financeflow.data.model.Transaction
import br.edu.utfpr.financeflow.data.repository.TransactionRepository

class FakeTransactionRepository(
    initial: List<Transaction> = emptyList()
) : TransactionRepository {

    private val stored = initial.toMutableList()
    private var nextId = 1L

    override fun insert(transaction: Transaction): Long {
        val id = nextId++
        stored.add(transaction.copy(id = id))
        return id
    }

    override fun findAll(): List<Transaction> =
        stored.sortedByDescending { it.dateMillis }

    override fun delete(id: Long): Int {
        val removed = stored.removeAll { it.id == id }
        return if (removed) 1 else 0
    }
}
