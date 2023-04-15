package com.grdkrll.kfinance.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.model.User
import com.grdkrll.kfinance.model.dto.transaction.response.TransactionResponse
import com.grdkrll.kfinance.repository.SortRepository
import com.grdkrll.kfinance.repository.SortType
import com.grdkrll.kfinance.repository.TransactionRepository
import com.grdkrll.kfinance.repository.UserRepository
import com.grdkrll.kfinance.sealed.TransactionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SortField(
    val sortType: SortType
)


class HomeViewModel(
    private val navigationDispatcher: NavigationDispatcher,
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val sortRepository: SortRepository
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortField(sortRepository.fetchSortType()))
    val sortType: StateFlow<SortField> = _sortType

    val response: MutableState<TransactionState> = mutableStateOf(TransactionState.Empty)

    fun getUser(): User? = userRepository.getUser()

    fun redirectToPreLogin() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.PRE_LOGIN)
        }
    }

    fun redirectToAddTransaction() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.navigate(NavDest.ADD_TRANSACTION)
        }
    }

    fun redirectToGroupsList() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.GROUPS_LIST)
        }
    }

    fun redirectToProfile() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.PROFILE)
        }
    }

    private fun List<TransactionResponse>.sortBy(sortQuery: SortType): List<TransactionResponse> {
        return when (sortQuery) {
            SortType.OLD -> {
                sortedBy { it.timestamp }.reversed()
            }

            SortType.RISING -> {
                sortedBy { it.sum }
            }

            SortType.FALLING -> {
                sortedBy { it.sum }.reversed()
            }

            else -> {
                sortedBy { it.timestamp }
            }
        }
    }

    fun fetchTransactions() {
        response.value = TransactionState.Loading
        viewModelScope.launch {
            val res = transactionRepository.getTransactions()
            if (res.isSuccess) {
                val page = res.getOrNull() ?: throw Exception()
                page.result = page.result.sortBy(sortRepository.fetchSortType())
                response.value = TransactionState.Success(page)
            }
        }
    }

    fun onSortTypeChanged(newSortType: String) {
        sortRepository.saveSortingType(newSortType)
        _sortType.value = sortType.value.copy(sortType = sortRepository.fetchSortType())
        when (val result = response.value) {
            is TransactionState.Success -> {
                val page = result.data
                page.result = page.result.sortBy(sortRepository.fetchSortType())
                response.value = TransactionState.Loading
                response.value = TransactionState.Success(page)
            }
            else -> {
            }
        }
    }
}