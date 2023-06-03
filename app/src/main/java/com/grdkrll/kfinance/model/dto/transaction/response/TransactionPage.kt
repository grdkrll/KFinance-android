package com.grdkrll.kfinance.model.dto.transaction.response

import kotlinx.serialization.Serializable

/**
 * A Response class used to get a List of Transactions of the User
 *
 * @property result holds the List of returned Transactions
 * @property totalCount total amount of Transactions
 * @property totalPages how many pages can be displayed (totalCount / 5)
 */
@Serializable
class TransactionPage(
    var result: List<TransactionResponse>,
    var totalCount: Int
) {
    var totalPages = totalCount / 5
}