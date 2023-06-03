package com.grdkrll.kfinance.model.dto.transaction.response

import com.grdkrll.kfinance.TransactionCategory
import kotlinx.serialization.Serializable

/**
 * A Response class used to hold data of a single Transaction
 *
 * @property id the id of the Transaction in the backend database
 * @property message the message of the Transaction in the backend database
 * @property type indicates whether the Transaction belongs to a Group or solely to the User (false if the Transactions belongs only to the User)
 * @property category the category of the Transaction
 * @property sum the total sum of the Transaction (if the Transaction belongs to the Expenses category, sum is negative)
 * @property timestamp the time when the transaction was added to the backend (string holds Instant datatype converted to string)
 * @property ownerHandle indicates the id of the Group (0 if the Transactions belongs only to the User)
 */
@Serializable
class TransactionResponse(
    val id: Int,
    val message: String,
    val type: Boolean,
    val category: TransactionCategory,
    val sum: Double,
    val timestamp: String,
    val ownerHandle: String
)