package com.grdkrll.kfinance.model.dto.transaction.request

import kotlinx.serialization.Serializable

@Serializable
class GroupTransactionRequest(
    val groupId: Int
)