package com.grdkrll.kfinance.service

import com.grdkrll.kfinance.TimePeriodType
import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.model.dto.transaction.request.TransactionRequest
import io.ktor.client.statement.*

/**
 * A Service Class used to make API calls to the backend about Transactions
 */
interface TransactionService {
    /**
     * Used to make an API call to the backend to create a new Transaction
     *
     * @param transaction an instance of [TransactionRequest]
     * @param token a JWT token to Authorize the call
     */
    suspend fun addTransaction(transaction: TransactionRequest, token: String?): HttpResponse

    /**
     * Used to make an API call to the backend to get a Page of Transactions
     *
     * @param groupId the id of the Group (default value is 0 that means that no group is selected)
     * @param recent indicates how many transactions to return (if recent == 1 5 transactions are returned, otherwise 30 transactions are returned)
     * @param page indicated for which page to return transactions
     * @param category the category by which to filter the transactions (default value is to return all transactions)
     * @param timePeriod the time period by which to filter the transactions (default value is to return all transactions)
     * @param token a JWT token to Authorize the call
     */
    suspend fun getPage(groupId: Int = 0, recent: Int, page: Int = 0, category: TransactionCategory = TransactionCategory.ALL, timePeriod: TimePeriodType = TimePeriodType.ALL, token: String?): HttpResponse

    /**
     * Used to make an API call to the backend to get a total sum of transactions
     *
     * @param groupId the id of the Group (default value is 0 that means that no group is selected)
     * @param category the category by which to filter the transactions (default value is to return all transactions)
     * @param timePeriod the time period by which to filter the transactions (default value is to return all transactions)
     * @param token a JWT token to Authorize the call
     */
    suspend fun getTotal(groupId: Int, timePeriod: TimePeriodType, category: TransactionCategory, token: String?): HttpResponse
}