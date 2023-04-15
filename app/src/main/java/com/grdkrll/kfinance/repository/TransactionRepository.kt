package com.grdkrll.kfinance.repository

import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.model.database.TransactionDatabase
import com.grdkrll.kfinance.model.dto.transaction.request.TransactionRequest
import com.grdkrll.kfinance.model.dto.transaction.response.TransactionPage
import com.grdkrll.kfinance.model.dto.transaction.response.TransactionResponse
import com.grdkrll.kfinance.model.table.TransactionEntity
import com.grdkrll.kfinance.service.transaction.TransactionService
import io.ktor.client.call.*

class TransactionRepository(
    private val transactionService: TransactionService,
    private val tokenRepository: TokenRepository,
    private val database: TransactionDatabase
) {
    suspend fun getTransactions(page: Int = 0): Result<TransactionPage> {
        try {
            val data =
                transactionService.getAll(tokenRepository.fetchAuthToken()).body<TransactionPage>()
            data.result.map {
                database.getTransactionDao().addTransaction(
                    TransactionEntity(
                        it.id,
                        it.type,
                        it.category.name,
                        it.sum,
                        it.timestamp
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
                    it.type,
                    TransactionCategory.valueOf(it.category),
                    it.sum,
                    it.timestamp
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
                    res.type,
                    res.category.name,
                    res.sum,
                    res.timestamp
                )
            )
            return Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}