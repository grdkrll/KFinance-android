package com.grdkrll.kfinance.model.dto.transaction.request

import com.grdkrll.kfinance.TransactionCategory
import kotlinx.serialization.Serializable

/**
 * A Request class used to add a new Transaction to the backend database
 *
 * @param message the message of the Transaction
 * @param type indicates whether the Transaction belongs to a Group or solely to the User (false if the Transactions belongs only to the User)
 * @param groupId indicates the id of the Group (0 if the Transactions belongs only to the User)
 * @param category the category of the Transaction
 * @param sum the total sum of the Transaction
 */
@Serializable
class TransactionRequest(
    val message: String,
    val type: Boolean,
    val groupId: Int,
    val category: TransactionCategory,
    val sum: Double
)