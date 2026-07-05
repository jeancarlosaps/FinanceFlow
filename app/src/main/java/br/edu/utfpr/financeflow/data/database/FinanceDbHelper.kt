package br.edu.utfpr.financeflow.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FinanceDbHelper(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(FinanceContract.SQL_CREATE_TRANSACTIONS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(FinanceContract.SQL_DROP_TRANSACTIONS)
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "financeflow.db"
        const val DATABASE_VERSION = 1
    }
}
