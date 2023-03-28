package com.grdkrll.kfinance.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grdkrll.kfinance.model.dao.TransactionDao
import com.grdkrll.kfinance.model.table.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun getTransactionDao() : TransactionDao
}