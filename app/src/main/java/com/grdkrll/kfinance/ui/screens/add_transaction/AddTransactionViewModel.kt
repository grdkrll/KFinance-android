package com.grdkrll.kfinance.ui.screens.add_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.model.dto.transaction.request.TransactionRequest
import com.grdkrll.kfinance.repository.TransactionRepository
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.SumInputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val navigationDispatcher: NavigationDispatcher,
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    private val _type = MutableStateFlow(false)

    private var _category = MutableStateFlow("")
    val category: StateFlow<String> = _category

    private val _sum = MutableStateFlow(SumInputField())
    val sum: StateFlow<SumInputField> = _sum

    fun onCategoryChanged(newCategoryValue: String) {
        _category.value = newCategoryValue
    }

    fun onSumChanged(newSumValue: String) {

        _sum.value = sum.value.copy(inputField = newSumValue.toDouble(), isError = false, errorMessage = "")
    }

    fun onAddTransactionButtonClicked() {
        viewModelScope.launch {
            val res = transactionRepository.addTransaction(
                TransactionRequest(
                    false,
                    TransactionCategory.SALARY,
                    sum.value.inputField
                )
            )
            if(res.isSuccess) {
                navigationDispatcher.dispatchNavigationCommand { navController ->
                    navController.popBackStack()
                    navController.navigate(NavDest.HOME)
                }
            }
        }
    }

    fun onCloseButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.HOME)
        }
    }
}