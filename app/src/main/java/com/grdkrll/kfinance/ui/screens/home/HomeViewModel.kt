package com.grdkrll.kfinance.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.model.Group
import com.grdkrll.kfinance.model.User
import com.grdkrll.kfinance.model.dto.transaction.response.TransactionResponse
import com.grdkrll.kfinance.repository.GroupRepository
import com.grdkrll.kfinance.repository.SortRepository
import com.grdkrll.kfinance.repository.SortType
import com.grdkrll.kfinance.repository.TimePeriodType
import com.grdkrll.kfinance.repository.TransactionRepository
import com.grdkrll.kfinance.repository.UserRepository
import com.grdkrll.kfinance.sealed.TransactionState
import com.grdkrll.kfinance.ui.screens.add_transaction.CategoryInputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SortField(
    val sortType: SortType
)

data class TimePeriodField(
    val period: Int = 0
)

data class IntField(
    val value: Double
)

class HomeViewModel(
    private val navigationDispatcher: NavigationDispatcher,
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val sortRepository: SortRepository,
    private val groupRepository: GroupRepository
) : ViewModel() {

    val numberOfPeriods = 3
    val periodsList = listOf("Today", "This week", "This month")
    private val periodQueryList = TimePeriodType.values()

    private val _selectedPeriod = MutableStateFlow(TimePeriodField())
    val selectedPeriod: StateFlow<TimePeriodField> = _selectedPeriod
    private val _periodSum = MutableStateFlow(IntField(0.toDouble()))
    var periodSum: StateFlow<IntField> = _periodSum

    private val _category = MutableStateFlow(CategoryInputField(TransactionCategory.ALL))
    val category: StateFlow<CategoryInputField> = _category

    private val _sortType = MutableStateFlow(SortField(sortRepository.fetchSortType()))
    val sortType: StateFlow<SortField> = _sortType


    val response: MutableState<TransactionState> = mutableStateOf(TransactionState.Empty)
    val responseTotal: MutableState<Boolean> = mutableStateOf(false)

    fun getUser(): Pair<User, Group>? = userRepository.getUser()

    fun redirectToPreLogin() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.LOGIN)
        }
    }

    fun onPeriodClicked(i: Int) {
        _selectedPeriod.value = selectedPeriod.value.copy(period = i)
        fetchTransactions()
        fetchTotal()
    }

    fun onCategoryChanged(newCategoryValue: String) {
        _category.value =
            category.value.copy(category = TransactionCategory.valueOf(newCategoryValue))
        fetchTransactions()
        fetchTotal()
    }

    fun onDeselectGroupButtonClicked() {
        groupRepository.deselectGroup()
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.HOME)
        }
    }

    fun onAddTransactionButtonClicked() {
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

    fun redirectToAllTransactions() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.navigate(NavDest.ALL_TRANSACTIONS)
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
            val res = transactionRepository.getTransactions(
                1,
                transactionCategory = category.value.category,
                timePeriod = periodQueryList[selectedPeriod.value.period]
            )
            if (res.isSuccess) {
                val page = res.getOrNull() ?: throw Exception()
                page.result = page.result.sortBy(sortRepository.fetchSortType())
                response.value = TransactionState.Success(page)
            }
        }
    }

    fun fetchTotal() {
        val userGroup = getUser() ?: return
        val (user, group) = userGroup
        val groupId = if(group.id != -1) group.id else 0
        responseTotal.value = false
        viewModelScope.launch {
            val res = transactionRepository.getTotal(
                groupId,
                periodQueryList[selectedPeriod.value.period],
                category.value.category
            )
            if(res.isSuccess) {
                _periodSum.value = periodSum.value.copy(value = res.getOrNull()?.total ?: 0.toDouble())
                responseTotal.value = true
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