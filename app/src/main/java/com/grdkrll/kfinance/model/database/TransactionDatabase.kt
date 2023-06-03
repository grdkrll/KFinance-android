package com.grdkrll.kfinance.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grdkrll.kfinance.model.dao.TransactionDao
import com.grdkrll.kfinance.model.table.TransactionEntity

/**
 * An abstract class of Room Database used to store Transactions which User created
 */
@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class TransactionDatabase : RoomDatabase() {

    /**
     * Returns an instance of DAO to communicate with the local database
     *
     * @return an instance of DAO to communicate with the local database
     */
    abstract fun getTransactionDao(): TransactionDao
}