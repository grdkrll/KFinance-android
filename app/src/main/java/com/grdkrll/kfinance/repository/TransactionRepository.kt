package com.grdkrll.kfinance.repository

import com.grdkrll.kfinance.TimePeriodType
import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.model.database.TransactionDatabase
import com.grdkrll.kfinance.model.dto.transaction.request.TransactionRequest
import com.grdkrll.kfinance.model.dto.transaction.response.TotalResponse
import com.grdkrll.kfinance.model.dto.transaction.response.TransactionPage
import com.grdkrll.kfinance.model.dto.transaction.response.TransactionResponse
import com.grdkrll.kfinance.model.table.TransactionEntity
import com.grdkrll.kfinance.service.TransactionService
import io.ktor.client.call.*

/**
 * A Repository class used to make calls about Transactions to the local database and the backend
 *
 * @param transactionService an instance of [TransactionService]
 * @param tokenRepository an instance of [TokenRepository]
 * @param database an instance of [TransactionDatabase]
 * @param selectedGroupRepository an instance of [SelectedGroupRepository]
 */
class TransactionRepository(
    private val transactionService: TransactionService,
    private val tokenRepository: TokenRepository,
    private val database: TransactionDatabase,
    private val selectedGroupRepository: SelectedGroupRepository
) {
    /**
     * Used to make an API call to the backend or call to the local database to get transactions for the [page]
     *
     * @param recent indicates the number of transactions to return. 5 if recent is set to 1, 30 otherwise
     * @param page the page number for which to return the transactions
     * @param transactionCategory the category by which to filter the transactions (defaults is set to return all transactions)
     * @param timePeriod the time period by which to filter the transactions (default is set to return all transactions)
     *
     * @return an instance of [Result] that if successful holds an instance of [TransactionPage]
     */
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

    /**
     * Used to make an API call to the backend to add a new Transaction
     *
     * @param transactionRequest an instance of [TransactionRequest] that holds data for the new Transaction
     *
     * @return an instance of [Result] that if successful holds an instance of [TransactionResponse]
     */
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

    /**
     * Used to make an API call to get a total sum of transactions of the User
     *
     * @param category the category by which to filter the transactions
     * @param timePeriod the time period by which to filter the transactions
     *
     * @return an instance of [Result] that if successful holds an instance of [TotalResponse]
     */
    suspend fun getTotal(
        timePeriod: TimePeriodType,
        category: TransactionCategory
    ): Result<TotalResponse> {
        val group = selectedGroupRepository.fetchGroup()
        return try {
            val res = transactionService.getTotal(
                if (group.id == -1) 0 else group.id,
                timePeriod,
                category,
                tokenRepository.fetchAuthToken()
            ).body<TotalResponse>()
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}