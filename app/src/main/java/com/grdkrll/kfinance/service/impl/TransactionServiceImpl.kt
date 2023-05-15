package com.grdkrll.kfinance.service.impl

import com.grdkrll.kfinance.SERVICE_BASE_URL
import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.model.dto.transaction.request.GroupTransactionRequest
import com.grdkrll.kfinance.model.dto.transaction.request.TransactionRequest
import com.grdkrll.kfinance.repository.TimePeriodType
import com.grdkrll.kfinance.service.TransactionService
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

    override suspend fun getPage(groupId: Int, recent: Int, page: Int, category: TransactionCategory, timePeriod: TimePeriodType, token: String?) = client.get("$BASE_URL/t/get_all_transactions") {
        header(AuthScheme.Bearer, token)
        url {
            parameters.append("group_id", groupId.toString())
            parameters.append("recent", recent.toString())
            parameters.append("page", page.toString())
            parameters.append("category", category.name)
            parameters.append("time", timePeriod.name)
        }
    }
    override suspend fun getTotal(
        groupId: Int,
        timePeriod: TimePeriodType,
        category: TransactionCategory,
        token: String?
    ) = client.get("$BASE_URL/t/total") {
        header(AuthScheme.Bearer, token)
        contentType(ContentType.Application.Json)
        url {
            parameters.append("groupId", groupId.toString())
            parameters.append("category", category.name)
            parameters.append("time", timePeriod.name)
        }
    }
}