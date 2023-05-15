package com.grdkrll.kfinance.model.dto.transaction.request

import com.grdkrll.kfinance.TransactionCategory
import kotlinx.serialization.Serializable

@Serializable
class TransactionRequest(
    val message: String,
    val type: Boolean,
    val groupId: Int,
    val category: TransactionCategory,
    val sum: Double
)