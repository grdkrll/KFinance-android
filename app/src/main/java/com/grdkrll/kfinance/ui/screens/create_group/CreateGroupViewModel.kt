package com.grdkrll.kfinance.ui.screens.create_group

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.checkHandle
import com.grdkrll.kfinance.checkPassword
import com.grdkrll.kfinance.repository.GroupRepository
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * A View Model Class for Create Group Screen
 *
 * @property name the name that is currently held in the Input Field
 * @property handle the handle that is currently held in the Input Field
 * @property password the password that is currently held in the Input Field
 * @property loading indicates that an API call to create a Group is in process
 */
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

    /**
     * Used to change [name] whenever value inside Input Field changes
     */
    fun onNameChanged(newName: String) {
        _name.value = name.value.copy(inputField = newName)
    }

    /**
     * Used to check change [handle] whenever value inside Input Field changes. (if the [handle] is not correct puts an error in the Input Field)
     */
    fun onHandleChanged(newHandle: String) {
        _handle.value = handle.value.copy(
            inputField = newHandle,
            isError = checkHandle(newHandle),
            errorMessage = "Please, only use letters, numbers and underscores in your handle"
        )
    }

    /**
     * Used to change [password] whenever value inside Input Field changes. (if the [password] is not correct puts an error in the Input Field)
     */
    fun onPasswordChanged(newPassword: String) {
        val error = checkPassword(newPassword)
        _password.value = password.value.copy(
            inputField = newPassword,
            isError = error != null,
            errorMessage = error ?: ""
        )
    }

    /**
     * Used check that all fields are correct, and if so makes an API call to the backend to create a new Group
     */
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

    /**
     * Used to redirect to Join Group Screen
     */
    fun onJoinGroupButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.JOIN_GROUP)
        }
    }

    /**
     * Used to close the Create Group Screen
     */
    fun onCloseButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
        }
    }
}