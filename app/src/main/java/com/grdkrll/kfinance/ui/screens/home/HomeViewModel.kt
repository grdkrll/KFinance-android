package com.grdkrll.kfinance.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.TimePeriodType
import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.model.Group
import com.grdkrll.kfinance.model.User
import com.grdkrll.kfinance.model.dto.transaction.response.TransactionResponse
import com.grdkrll.kfinance.repository.GroupRepository
import com.grdkrll.kfinance.repository.TransactionRepository
import com.grdkrll.kfinance.repository.UserRepository
import com.grdkrll.kfinance.sealed.TransactionState
import com.grdkrll.kfinance.ui.screens.add_transaction.CategoryInputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * A Data Class used for Stateful display of current Time Period
 */
data class TimePeriodField(
    val period: Int = 0
)

/**
 * A Data Class used for Stateful display of Total Sum
 */
data class TotalSumField(
    val value: Double
)

/**
 * A View Model Class for Home Screen
 *
 * @property numberOfPeriods total number of currently available Time Periods
 * @property periodsList a List of currently available Time Periods in String form
 * @property periodQueryList a List of currently available Time Periods in [TimePeriodType] form
 * @property selectedPeriod used to hold currently selected Time Period
 * @property periodSum used to hold total sum of all transactions
 * @property category used to hold currently selected category
 * @property response indicates whether the List of Transactions was fetched or not. When fetched holds a List of [TransactionResponse]
 * @property responseTotal indicates whether the total of all the transactions was fetched or not. When fetched holds an instance if [Boolean] with value true
 */
class HomeViewModel(
    private val navigationDispatcher: NavigationDispatcher,
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val groupRepository: GroupRepository
) : ViewModel() {

    val numberOfPeriods = 3
    val periodsList = listOf("Today", "This week", "This month")
    private val periodQueryList = TimePeriodType.values()

    private val _selectedPeriod = MutableStateFlow(TimePeriodField())
    val selectedPeriod: StateFlow<TimePeriodField> = _selectedPeriod
    private val _periodSum = MutableStateFlow(TotalSumField(0.toDouble()))
    var periodSum: StateFlow<TotalSumField> = _periodSum

    private val _category = MutableStateFlow(CategoryInputField(TransactionCategory.ALL))
    val category: StateFlow<CategoryInputField> = _category

    val response: MutableState<TransactionState> = mutableStateOf(TransactionState.Empty)
    val responseTotal: MutableState<Boolean> = mutableStateOf(false)

    /**
     * Used to get a basic information about the User and the Selected Group
     *
     * @return if User is not Signed In returns null, otherwise returns a [Pair] of [User], [Group] (if no Group is Selected [Group] id field will be equal to -1)
     */
    fun getUser(): Pair<User, Group>? = userRepository.getUser()

    /**
     * Used to redirect to Login Screen
     */
    fun redirectToPreLogin() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.LOGIN)
        }
    }

    /**
     * Used to change [selectedPeriod] whenever a new Time Period is selected
     */
    fun onPeriodClicked(i: Int) {
        _selectedPeriod.value = selectedPeriod.value.copy(period = i)
        fetchTransactions()
        fetchTotal()
    }

    /**
     * Used to change [category] whenever a new Category is selected
     */
    fun onCategoryChanged(newCategoryValue: String) {
        _category.value =
            category.value.copy(category = TransactionCategory.valueOf(newCategoryValue))
        fetchTransactions()
        fetchTotal()
    }

    /**
     * Used to deselect the Selected Group
     */
    fun onDeselectGroupButtonClicked() {
        groupRepository.deselectGroup()
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.HOME)
        }
    }

    /**
     * Used to redirect to Add Transactions Screen
     */
    fun onAddTransactionButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.navigate(NavDest.ADD_TRANSACTION)
        }
    }

    /**
     * Used to redirect to Groups List Screen
     */
    fun redirectToGroupsList() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.GROUPS_LIST)
        }
    }

    /**
     * Used to redirect to Profile Screen
     */
    fun redirectToProfile() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.PROFILE)
        }
    }

    /**
     * Used to redirect to All Transactions Screen
     */
    fun redirectToAllTransactions() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.navigate(NavDest.ALL_TRANSACTIONS)
        }
    }

    /**
     * Used to make an API call to the backend to fetch a List of recent transactions of the User
     */
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
                response.value = TransactionState.Success(page)
            }
        }
    }

    /**
     * Used to make an API call to the backend to fetch a total sum of all the transactions of the User
     */
    fun fetchTotal() {
        responseTotal.value = false
        viewModelScope.launch {
            val res = transactionRepository.getTotal(
                periodQueryList[selectedPeriod.value.period],
                category.value.category
            )
            if (res.isSuccess) {
                _periodSum.value =
                    periodSum.value.copy(value = res.getOrNull()?.total ?: 0.toDouble())
                responseTotal.value = true
            }
        }
    }
}