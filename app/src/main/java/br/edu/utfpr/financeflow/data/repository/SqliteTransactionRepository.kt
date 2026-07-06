package br.edu.utfpr.financeflow.data.repository

import android.content.ContentValues
import br.edu.utfpr.financeflow.data.database.FinanceContract.TransactionEntry
import br.edu.utfpr.financeflow.data.database.FinanceDbHelper
import br.edu.utfpr.financeflow.data.model.Transaction
import br.edu.utfpr.financeflow.data.model.TransactionType

class SqliteTransactionRepository(
    private val dbHelper: FinanceDbHelper
) : TransactionRepository {

    override fun insert(transaction: Transaction): Long {
        val values = ContentValues().apply {
            put(TransactionEntry.COLUMN_DESCRIPTION, transaction.description)
            put(TransactionEntry.COLUMN_AMOUNT, transaction.amount)
            put(TransactionEntry.COLUMN_DATE_MILLIS, transaction.dateMillis)
            put(TransactionEntry.COLUMN_TYPE, transaction.type.name)
        }
        return dbHelper.writableDatabase.insert(TransactionEntry.TABLE_NAME, null, values)
    }

    override fun delete(id: Long): Int {
        return dbHelper.writableDatabase.delete(
            TransactionEntry.TABLE_NAME,
            "${TransactionEntry.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    override fun findAll(): List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        val cursor = dbHelper.readableDatabase.query(
            TransactionEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "${TransactionEntry.COLUMN_DATE_MILLIS} DESC, ${TransactionEntry.COLUMN_ID} DESC"
        )
        cursor.use {
            val idIndex = it.getColumnIndexOrThrow(TransactionEntry.COLUMN_ID)
            val descIndex = it.getColumnIndexOrThrow(TransactionEntry.COLUMN_DESCRIPTION)
            val amountIndex = it.getColumnIndexOrThrow(TransactionEntry.COLUMN_AMOUNT)
            val dateIndex = it.getColumnIndexOrThrow(TransactionEntry.COLUMN_DATE_MILLIS)
            val typeIndex = it.getColumnIndexOrThrow(TransactionEntry.COLUMN_TYPE)
            while (it.moveToNext()) {
                transactions.add(
                    Transaction(
                        id = it.getLong(idIndex),
                        description = it.getString(descIndex),
                        amount = it.getDouble(amountIndex),
                        dateMillis = it.getLong(dateIndex),
                        type = TransactionType.valueOf(it.getString(typeIndex))
                    )
                )
            }
        }
        return transactions
    }
}
