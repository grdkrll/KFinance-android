package com.grdkrll.kfinance.ui.screens.add_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.model.dto.transaction.request.TransactionRequest
import com.grdkrll.kfinance.repository.SelectedGroupRepository
import com.grdkrll.kfinance.repository.TransactionRepository
import com.grdkrll.kfinance.repository.UserRepository
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CategoryInputField(
    val category: TransactionCategory
)

class AddTransactionViewModel(
    private val navigationDispatcher: NavigationDispatcher,
    private val transactionRepository: TransactionRepository,
    private val selectedGroupRepository: SelectedGroupRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _message = MutableStateFlow(InputField())
    val message: StateFlow<InputField> = _message

    private val _sum = MutableStateFlow(InputField())
    val sum: StateFlow<InputField> = _sum

    private val _category = MutableStateFlow(CategoryInputField(TransactionCategory.FOOD))
    val category: StateFlow<CategoryInputField> = _category

    fun onMessageChanged(newMessage: String) {
        _message.value = message.value.copy(inputField = newMessage)
    }

    fun onSumChanged(newSumValue: String) {
        if (newSumValue.toDoubleOrNull() != null) {
            _sum.value =
                sum.value.copy(inputField = newSumValue, isError = false, errorMessage = "")
        } else {
            _sum.value = sum.value.copy(isError = true, errorMessage = "Sum should be a number")
        }
    }

    fun onCategoryChanged(newCategoryValue: String) {
        _category.value =
            category.value.copy(category = TransactionCategory.valueOf(newCategoryValue))
    }

    fun onAddTransactionButtonClicked() {
        if (sum.value.inputField.toDoubleOrNull() == null) {
            return
        }
        viewModelScope.launch {
            val userGroup = userRepository.getUser() ?: throw Exception("Something went wrong")
            val (user, group) = userGroup
            val res = transactionRepository.addTransaction(
                TransactionRequest(
                    message = message.value.inputField,
                    type = group.id != -1,
                    groupId = if (group.id != -1) group.id else user.id,
                    category = category.value.category,
                    sum = sum.value.inputField.toDouble()
                )
            )
            if (res.isSuccess) {
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
        }
    }
}