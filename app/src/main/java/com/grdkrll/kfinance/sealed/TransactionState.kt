package com.grdkrll.kfinance.sealed

import com.grdkrll.kfinance.model.dto.transaction.response.TransactionPage

/**
 * A Sealed State Class Used to load a List of Transactions
 */
sealed class TransactionState {
    class Success(val data: TransactionPage) : TransactionState()
    class Failure(val message: String) : TransactionState()
    object Loading : TransactionState()
    object Empty : TransactionState()
}
