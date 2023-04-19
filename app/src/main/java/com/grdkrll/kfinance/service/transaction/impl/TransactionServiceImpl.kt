package com.grdkrll.kfinance.service.transaction.impl

import com.grdkrll.kfinance.SERVICE_BASE_URL
import com.grdkrll.kfinance.model.dto.transaction.request.GroupTransactionRequest
import com.grdkrll.kfinance.model.dto.transaction.request.TransactionRequest
import com.grdkrll.kfinance.service.transaction.TransactionService
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
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
        contentType(ContentType.Application.Json)
        setBody(transaction)
    }

    override suspend fun getAllByUser(token: String?) = client.get("$BASE_URL/t/u") {
        header(AuthScheme.Bearer, token)
    }

    override suspend fun getAllByGroup(token: String?, groupId: Int): HttpResponse = client.get("$BASE_URL/t/g") {
        header(AuthScheme.Bearer, token)
        contentType(ContentType.Application.Json)
        setBody(GroupTransactionRequest(groupId))
    }
}