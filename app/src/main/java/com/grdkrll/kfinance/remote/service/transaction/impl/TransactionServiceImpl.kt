package com.grdkrll.kfinance.remote.service.transaction.impl

import android.util.Log
import com.grdkrll.kfinance.SERVICE_BASE_URL
import com.grdkrll.kfinance.model.dto.transaction.request.TransactionRequest
import com.grdkrll.kfinance.remote.service.transaction.TransactionService
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.auth.*

class TransactionServiceImpl(
    private val client: HttpClient
) : TransactionService {
    companion object {
        private const val BASE_URL = SERVICE_BASE_URL
    }

    override suspend fun addTransaction(transaction: TransactionRequest, token: String?) = client.post("$BASE_URL/t/add_transaction") {
        header(AuthScheme.Bearer, token)
        Log.d("Transaction Service", token ?: "null")
        contentType(ContentType.Application.Json)
        setBody(transaction)
    }

    override suspend fun getAll(token: String?) = client.get("$BASE_URL/t/get") {
        header(AuthScheme.Bearer, token)
    }
}