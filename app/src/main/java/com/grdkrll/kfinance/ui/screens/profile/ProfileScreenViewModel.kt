package com.grdkrll.kfinance.ui.screens.profile

import android.security.keystore.UserNotAuthenticatedException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.repository.UserRepository
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val userRepository: UserRepository,
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {
    private val _handle = MutableStateFlow(InputField())
    val handle: StateFlow<InputField> = _handle

    private val _email = MutableStateFlow(InputField())
    val email: StateFlow<InputField> = _email

    private val _password = MutableStateFlow(InputField())
    val password: StateFlow<InputField> = _password

    private val _confirmPassword = MutableStateFlow(InputField())
    val confirmPassword: StateFlow<InputField> = _confirmPassword

    init {
        val userGroup = userRepository.getUser() ?: throw UserNotAuthenticatedException()
        val (user, group) = userGroup
        _handle.value = handle.value.copy(inputField = user.handle)
        _email.value = email.value.copy(inputField = user.email)
    }

    fun onHandleChange(newHandle: String) {
        _handle.value = handle.value.copy(
            inputField = newHandle
        )
    }

    fun onEmailChange(newEmail: String) {
        _email.value = email.value.copy(
            inputField = newEmail
        )
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = password.value.copy(
            inputField = newPassword
        )
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _confirmPassword.value = confirmPassword.value.copy(
            inputField = newConfirmPassword
        )
    }

    fun onConfirmButtonClicked() {
        viewModelScope.launch {
            val res = userRepository.changeUserData(
                handle = handle.value.inputField,
                email = email.value.inputField,
                password = password.value.inputField,
                confirmPassword = confirmPassword.value.inputField
            )
            if(res.isSuccess) {
                navigationDispatcher.dispatchNavigationCommand { navController ->
                    navController.popBackStack()
                    navController.navigate(NavDest.HOME)
                }
            } else {
                _confirmPassword.value = confirmPassword.value.copy(isError = true, errorMessage = "Wrong password")
            }
        }
    }

    fun onRedirectToHomeClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.HOME)
        }
    }

    fun onRedirectToGroupsListClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.GROUPS_LIST)
        }
    }
}