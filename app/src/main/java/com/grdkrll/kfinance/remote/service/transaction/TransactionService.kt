package com.grdkrll.kfinance.remote.service.transaction

import com.grdkrll.kfinance.model.dto.transaction.request.TransactionRequest
import io.ktor.client.statement.*

interface TransactionService {
    suspend fun addTransaction(transaction: TransactionRequest, token: String?): HttpResponse

    suspend fun getAll(token: String?): HttpResponse
}