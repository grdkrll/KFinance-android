package com.grdkrll.kfinance.service.transaction

import com.grdkrll.kfinance.model.dto.transaction.request.TransactionRequest
import io.ktor.client.statement.*

interface TransactionService {
    suspend fun addTransaction(transaction: TransactionRequest, token: String?): HttpResponse

    suspend fun getAllByUser(token: String?): HttpResponse

    suspend fun getAllByGroup(token: String?, groupId: Int): HttpResponse
}