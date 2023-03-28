package com.grdkrll.kfinance.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.model.User
import com.grdkrll.kfinance.model.dto.transaction.response.TransactionPage
import com.grdkrll.kfinance.repository.TransactionRepository
import com.grdkrll.kfinance.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val navigationDispatcher: NavigationDispatcher,
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {



    fun getUser(): User? = userRepository.getUser()

    fun redirectToPreLogin() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.PRE_LOGIN)
        }
    }

    fun getTransactions() : TransactionPage {
        lateinit var transactionsPage: TransactionPage
        viewModelScope.launch {
            val res = transactionRepository.getTransactions()
            if(res.isSuccess) {
                transactionsPage = res.getOrThrow()
            }
        }
        return transactionsPage
    }

    fun redirectToAddTransaction() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.ADD_TRANSACTION)
        }
        return
    }

}