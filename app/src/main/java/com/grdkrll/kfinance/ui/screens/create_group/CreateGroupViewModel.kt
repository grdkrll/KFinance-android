package com.grdkrll.kfinance.ui.screens.create_group

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.checkEmail
import com.grdkrll.kfinance.checkHandle
import com.grdkrll.kfinance.checkPassword
import com.grdkrll.kfinance.repository.GroupRepository
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateGroupViewModel(
    private val groupRepository: GroupRepository,
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {
    private val _name = MutableStateFlow(InputField())
    val name: StateFlow<InputField> = _name

    private val _handle = MutableStateFlow(InputField())
    val handle: StateFlow<InputField> = _handle

    private val _password = MutableStateFlow(InputField())
    val password: StateFlow<InputField> = _password

    val loading: MutableState<Boolean> = mutableStateOf(false)

    fun onNameChanged(newName: String) {
        _name.value = name.value.copy(inputField = newName)
    }

    fun onHandleChanged(newHandle: String) {
        _handle.value = handle.value.copy(
            inputField = newHandle,
            isError = checkHandle(newHandle),
            errorMessage = "Please, only use letters, numbers and underscores in your handle"
        )
    }

    fun onPasswordChanged(newPassword: String) {
        val error = checkPassword(newPassword)
        _password.value = password.value.copy(
            inputField = newPassword,
            isError = error != null,
            errorMessage = error ?: ""
        )
    }

    fun onCreateGroupButtonClicked() {
        val passwordError = checkPassword(password.value.inputField)
        val handleError = checkHandle(handle.value.inputField)
        if (passwordError != null || handleError) {
            return
        }
        viewModelScope.launch {
            loading.value = true
            val res = groupRepository.createGroup(
                name = name.value.inputField,
                handle = handle.value.inputField,
                password = password.value.inputField
            )
            if(res.isSuccess) {
                navigationDispatcher.dispatchNavigationCommand { navController ->
                    navController.popBackStack()
                    navController.navigate(NavDest.GROUPS_LIST)
                }
            }
            loading.value = false
        }
    }

    fun onJoinGroupButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.JOIN_GROUP)
        }
    }

    fun onCloseButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
        }
    }
}