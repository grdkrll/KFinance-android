package com.grdkrll.kfinance.sealed

import com.grdkrll.kfinance.model.dto.transaction.response.TransactionPage

sealed class TransactionState {
    class Success(val data: TransactionPage) : TransactionState()
    class Failure(val message: String) : TransactionState()
    object Loading: TransactionState()
    object Empty: TransactionState()
}