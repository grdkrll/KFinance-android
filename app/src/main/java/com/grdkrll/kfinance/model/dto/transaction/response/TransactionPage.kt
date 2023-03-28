package com.grdkrll.kfinance.model.dto.transaction.response

import kotlinx.serialization.Serializable

@Serializable
class TransactionPage(
    var result: List<TransactionResponse>,
    var totalCount: Int
) {
    var totalPages = totalCount / 5
}