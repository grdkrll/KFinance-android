package com.grdkrll.kfinance.ui.screens.all_transactions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.repository.TransactionRepository
import com.grdkrll.kfinance.sealed.TransactionState
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.screens.add_transaction.CategoryInputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * A Data Class that serves as a shell for an integer parameter *page*
 */
data class PagerField(
    var page: Int
)

/**
 * A ViewModel Class for All Transactions Screen
 *
 * @property page the page that is currently displayed on the screen
 * @property category the category by which the list of transactions is currently filtered
 * @property response indicates whether transactions were fetched or not. Holds the list of transactions when it's fetched
 */
class AllTransactionsViewModel(
    private val navigationDispatcher: NavigationDispatcher,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _page = MutableStateFlow(PagerField(1))
    val page: StateFlow<PagerField> = _page

    private val _categoryField = MutableStateFlow(CategoryInputField(TransactionCategory.ALL))
    val category: StateFlow<CategoryInputField> = _categoryField

    val response: MutableState<TransactionState> = mutableStateOf(TransactionState.Empty)

    /**
     * Fetches transactions from the backend or the database
     */
    fun fetchTransactions() {
        response.value = TransactionState.Loading
        viewModelScope.launch {
            val res = transactionRepository.getTransactions(
                0,
                page = page.value.page,
                transactionCategory = category.value.category
            )
            if (res.isSuccess) {
                val page = res.getOrNull() ?: throw Exception()
                response.value = TransactionState.Success(page)
            }
        }
    }

    /**
     * Redirects back to home screen
     */
    fun onCloseButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.HOME)
        }
    }

    /**
     * Changes state of [category] field
     */
    fun onCategoryChanged(newCategory: String) {
        _categoryField.value =
            category.value.copy(category = TransactionCategory.valueOf(newCategory))
        fetchTransactions()
    }

    /**
     * Increments [page] by 1 and loads a new page of transactions
     */
    fun incrementPage() {
        when (val result = response.value) {
            is TransactionState.Success -> {
                if (page.value.page < result.data.totalPages) {
                    _page.value = page.value.copy(page = page.value.page + 1)
                    fetchTransactions()
                }
            }

            else -> {

            }
        }
    }

    /**
     * Decrements [page] by 1 and loads a new page of transactions
     */
    fun decrementPage() {
        when (val result = response.value) {
            is TransactionState.Success -> {
                if (page.value.page > 1) {
                    _page.value = page.value.copy(page = page.value.page - 1)
                    fetchTransactions()
                }
            }

            else -> {

            }
        }
    }
}