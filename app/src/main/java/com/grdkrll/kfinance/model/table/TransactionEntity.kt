package com.grdkrll.kfinance.model.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("transactions")
data class TransactionEntity(
    @PrimaryKey
    @ColumnInfo("id")
    var id: Int,

    @ColumnInfo("type")
    var type: Boolean,

    @ColumnInfo("category")
    var category: String,

    @ColumnInfo("sum")
    var sum: Double,

    @ColumnInfo("timestamp")
    var timestamp: Long
)
