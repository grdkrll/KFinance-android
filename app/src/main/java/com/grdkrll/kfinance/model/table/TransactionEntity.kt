package com.grdkrll.kfinance.model.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * An Entity class used for storing Transaction
 *
 * @property id the id of the Transaction in the backend database
 * @property message the message of the Transaction in the backend database
 * @property type the type of the Transaction in the backend database (false if the Transactions belongs only to the User)
 * @property category the category of the Transaction in the backend database
 * @property sum the total sum of the Transaction in the backend database
 * @property timestamp the time when Transaction was created (the string holds an Instant class converted to String)
 * @property ownerHandle the handle of the User that created the Transaction stored in the backend database
 */
@Entity("transactions")
data class TransactionEntity(
    @PrimaryKey
    @ColumnInfo("id")
    var id: Int,

    @ColumnInfo("message")
    var message: String,

    @ColumnInfo("type")
    var type: Boolean,

    @ColumnInfo("category")
    var category: String,

    @ColumnInfo("sum")
    var sum: Double,

    @ColumnInfo("timestamp")
    var timestamp: String,

    @ColumnInfo("ownerHandle")
    var ownerHandle: String
)
