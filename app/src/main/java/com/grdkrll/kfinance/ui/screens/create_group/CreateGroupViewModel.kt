package com.grdkrll.kfinance.ui.screens.create_group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
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

    fun onNameChanged(newName: String) {
        _name.value = name.value.copy(inputField = newName)
    }

    fun onHandleChanged(newHandle: String) {
        _handle.value = handle.value.copy(inputField = newHandle)
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = password.value.copy(inputField = newPassword)
    }

    fun onCreateGroupButtonClicked() {
        viewModelScope.launch {
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
        }
    }

    fun onAddGroupButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.ADD_GROUP)
        }
    }

    fun onCloseButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
        }
    }
}