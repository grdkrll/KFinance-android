package com.grdkrll.kfinance.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grdkrll.kfinance.model.table.TransactionEntity

/**
 * DAO of Transactions which User created
 */
@Dao
interface TransactionDao {
    /**
     * Adds a new transactions to the local database
     *
     * @param transactionEntity holds data about the new transaction
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transactionEntity: TransactionEntity)

    /**
     * Returns a List of transactions stored in the local database by pages (5 at a time)
     *
     * @param page the page to return
     *
     * @return a List of transactions from requested page
     */
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC LIMIT 5 OFFSET :page * 5")
    suspend fun getTransactionsForPage(page: Int): List<TransactionEntity>


    /**
     * Returns a total number of transactions in the local database
     *
     * @return a total number of transactions in the local database
     */
    @Query("SELECT COUNT(*) FROM transactions")
    suspend fun getTotalCount(): Int
}