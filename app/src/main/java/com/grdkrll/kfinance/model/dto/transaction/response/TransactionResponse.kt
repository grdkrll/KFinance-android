package com.grdkrll.kfinance.model.dto.transaction.response

import com.grdkrll.kfinance.TransactionCategory
import kotlinx.serialization.Serializable

@Serializable
class TransactionResponse(
    val id: Int,
    val type: Boolean,
    val category: TransactionCategory,
    val sum: Double,
    val timestamp: Long
)