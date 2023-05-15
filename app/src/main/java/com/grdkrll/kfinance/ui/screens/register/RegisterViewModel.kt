package com.grdkrll.kfinance.ui.screens.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.checkEmail
import com.grdkrll.kfinance.checkHandle
import com.grdkrll.kfinance.checkPassword
import com.grdkrll.kfinance.repository.UserRepository
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val navigationDispatcher: NavigationDispatcher,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _name = MutableStateFlow(InputField())
    val name: StateFlow<InputField> = _name

    private val _email = MutableStateFlow(InputField())
    val email: StateFlow<InputField> = _email

    private val _handle = MutableStateFlow(InputField())
    val handle: StateFlow<InputField> = _handle

    private val _password = MutableStateFlow(InputField())
    val password: StateFlow<InputField> = _password

    private val _passwordConfirm = MutableStateFlow(InputField())
    val passwordConfirm: StateFlow<InputField> = _passwordConfirm

    val loading: MutableState<Boolean> = mutableStateOf(false)

    fun onNameChanged(newName: String) {
        _name.value = name.value.copy(inputField = newName)
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = email.value.copy(
            inputField = newEmail,
            isError = checkEmail(newEmail),
            errorMessage = "Please, enter real email"
        )
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

    fun onPasswordConfirmChanged(newConfirmPassword: String) {
        _passwordConfirm.value = passwordConfirm.value.copy(
            inputField = newConfirmPassword,
            isError = newConfirmPassword == passwordConfirm.value.inputField,
            errorMessage = "Passwords do not match"
        )
    }

    fun onRegisterButtonClicked() {
        val passwordError = checkPassword(password.value.inputField)
        _password.value = password.value.copy(
            isError = passwordError != null,
            errorMessage = passwordError ?: ""
        )
        val emailError = checkEmail(email.value.inputField)
        _email.value = email.value.copy(
            isError = emailError,
            errorMessage = "Please, enter real email"
        )
        val handleError = checkHandle(handle.value.inputField)
        _handle.value = handle.value.copy(
            isError = handleError,
            errorMessage = "Please, only use letters, numbers and underscores in your handle"
        )
        val passwordMatch = password.value.inputField == passwordConfirm.value.inputField
        _passwordConfirm.value = passwordConfirm.value.copy(
            isError = !passwordMatch,
            errorMessage = "Passwords do not match"
        )
        if (passwordError != null || !passwordMatch || emailError || handleError) {
            return
        }
        viewModelScope.launch {
            loading.value = true
            val res = userRepository.registerUser(
                name = name.value.inputField,
                email = email.value.inputField,
                handle = handle.value.inputField,
                password = password.value.inputField
            )
            if (res.isSuccess) {
                navigationDispatcher.dispatchNavigationCommand { navController ->
                    navController.popBackStack()
                    navController.navigate(NavDest.HOME)
                }
            }
            loading.value = false
        }
    }

    fun onRedirectToLoginClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.LOGIN)
        }
    }
}