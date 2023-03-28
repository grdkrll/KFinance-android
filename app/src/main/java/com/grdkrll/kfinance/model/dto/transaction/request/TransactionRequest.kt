package com.grdkrll.kfinance.model.dto.transaction.request

import com.grdkrll.kfinance.TransactionCategory
import kotlinx.serialization.Serializable

@Serializable
class TransactionRequest(
    val type: Boolean,
    val category: TransactionCategory,
    val sum: Double
)