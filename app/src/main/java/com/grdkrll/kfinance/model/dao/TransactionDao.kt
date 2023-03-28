package com.grdkrll.kfinance.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grdkrll.kfinance.model.table.TransactionEntity

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC LIMIT 5 OFFSET :page * 5")
    suspend fun getTransactionsForPage(page: Int): List<TransactionEntity>

    @Query("SELECT COUNT(*) FROM transactions")
    suspend fun getTotalCount(): Int
}