package com.grdkrll.kfinance.repository

import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.model.database.TransactionDatabase
import com.grdkrll.kfinance.model.dto.transaction.request.TransactionRequest
import com.grdkrll.kfinance.model.dto.transaction.response.TotalResponse
import com.grdkrll.kfinance.model.dto.transaction.response.TransactionPage
import com.grdkrll.kfinance.model.dto.transaction.response.TransactionResponse
import com.grdkrll.kfinance.model.table.TransactionEntity
import com.grdkrll.kfinance.service.TransactionService
import io.ktor.client.call.*
import java.time.Instant

enum class TimePeriodType {
    TODAY,
    THIS_WEEK,
    THIS_MONTH,
    ALL
}

class TransactionRepository(
    private val transactionService: TransactionService,
    private val tokenRepository: TokenRepository,
    private val database: TransactionDatabase,
    private val selectedGroupRepository: SelectedGroupRepository
) {
    suspend fun getTransactions(
        recent: Int,
        page: Int = 1,
        transactionCategory: TransactionCategory = TransactionCategory.ALL,
        timePeriod: TimePeriodType = TimePeriodType.ALL
    ): Result<TransactionPage> {
        try {
            val group = selectedGroupRepository.fetchGroup()
            val data = transactionService.getPage(
                if (group.id == -1) 0 else group.id,
                recent,
                page,
                transactionCategory,
                timePeriod,
                tokenRepository.fetchAuthToken()
            ).body<TransactionPage>()
            data.result.map {
                database.getTransactionDao().addTransaction(
                    TransactionEntity(
                        it.id,
                        it.message,
                        it.type,
                        it.category.name,
                        it.sum,
                        it.timestamp,
                        it.ownerHandle
                    )
                )
            }
            return Result.success(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return try {
            val data = database.getTransactionDao().getTransactionsForPage(page).map {
                TransactionResponse(
                    it.id,
                    it.message,
                    it.type,
                    TransactionCategory.valueOf(it.category),
                    it.sum,
                    it.timestamp,
                    it.ownerHandle
                )
            }
            val totalCount = database.getTransactionDao().getTotalCount()
            Result.success(TransactionPage(data, totalCount))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun addTransaction(transactionRequest: TransactionRequest): Result<TransactionResponse> {
        return try {
            val res = transactionService.addTransaction(
                transactionRequest,
                tokenRepository.fetchAuthToken()
            ).body<TransactionResponse>()
            database.getTransactionDao().addTransaction(
                TransactionEntity(
                    res.id,
                    res.message,
                    res.type,
                    res.category.name,
                    res.sum,
                    res.timestamp,
                    res.ownerHandle
                )
            )
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }

    }

    suspend fun getTotal(
        groupId: Int,
        timePeriod: TimePeriodType,
        category: TransactionCategory
    ): Result<TotalResponse> {
        return try {
            val res = transactionService.getTotal(
                groupId, timePeriod, category, tokenRepository.fetchAuthToken()
            ).body<TotalResponse>()
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}