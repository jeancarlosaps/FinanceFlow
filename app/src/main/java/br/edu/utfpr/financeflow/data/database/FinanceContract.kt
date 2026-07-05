package br.edu.utfpr.financeflow.data.database

object FinanceContract {

    object TransactionEntry {
        const val TABLE_NAME = "transactions"
        const val COLUMN_ID = "_id"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_DATE_MILLIS = "date_millis"
        const val COLUMN_TYPE = "type"
    }

    const val SQL_CREATE_TRANSACTIONS =
        "CREATE TABLE ${TransactionEntry.TABLE_NAME} (" +
            "${TransactionEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "${TransactionEntry.COLUMN_DESCRIPTION} TEXT NOT NULL, " +
            "${TransactionEntry.COLUMN_AMOUNT} REAL NOT NULL, " +
            "${TransactionEntry.COLUMN_DATE_MILLIS} INTEGER NOT NULL, " +
            "${TransactionEntry.COLUMN_TYPE} TEXT NOT NULL)"

    const val SQL_DROP_TRANSACTIONS =
        "DROP TABLE IF EXISTS ${TransactionEntry.TABLE_NAME}"
}
