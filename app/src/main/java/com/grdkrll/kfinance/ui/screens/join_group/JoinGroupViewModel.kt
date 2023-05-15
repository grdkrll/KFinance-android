package com.grdkrll.kfinance.ui.screens.join_group

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.repository.GroupRepository
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JoinGroupViewModel(
    private val groupRepository: GroupRepository,
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {

    private val _handle = MutableStateFlow(InputField())
    val handle: StateFlow<InputField> = _handle

    private val _password = MutableStateFlow(InputField())
    val password: StateFlow<InputField> = _password

    val loading: MutableState<Boolean> = mutableStateOf(false)

    fun onHandleChanged(newHandle: String) {
        _handle.value = handle.value.copy(inputField = newHandle)
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = password.value.copy(inputField = newPassword)
    }

    fun onJoinGroupButtonClicked() {
        viewModelScope.launch {
            loading.value = true
            val res = groupRepository.addMember(
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

    fun onCreateGroupButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.CREATE_GROUP)
        }
    }

    fun onCloseButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
        }
    }
}