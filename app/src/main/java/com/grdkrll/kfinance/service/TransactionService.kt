package com.grdkrll.kfinance.service

import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.model.dto.transaction.request.TransactionRequest
import com.grdkrll.kfinance.repository.TimePeriodType
import io.ktor.client.statement.*

interface TransactionService {
    suspend fun addTransaction(transaction: TransactionRequest, token: String?): HttpResponse
    suspend fun getPage(groupId: Int = 0, recent: Int, page: Int = 0, category: TransactionCategory = TransactionCategory.ALL, timePeriod: TimePeriodType = TimePeriodType.ALL, token: String?): HttpResponse
    suspend fun getTotal(groupId: Int, timePeriod: TimePeriodType, category: TransactionCategory, token: String?): HttpResponse
}