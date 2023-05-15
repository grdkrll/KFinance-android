package com.grdkrll.kfinance.ui.screens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.checkEmail
import com.grdkrll.kfinance.checkPassword
import com.grdkrll.kfinance.repository.UserRepository
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val navigationDispatcher: NavigationDispatcher,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _email = MutableStateFlow(InputField())
    val email: StateFlow<InputField> = _email

    private val _password = MutableStateFlow(InputField())
    val password: StateFlow<InputField> = _password

    val loading: MutableState<Boolean> = mutableStateOf(false)

    fun onEmailChanged(newEmail: String) {
        _email.value = email.value.copy(
            inputField = newEmail,
            isError = checkEmail(newEmail),
            errorMessage = "Please, enter real email"
        )
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = password.value.copy(inputField = newPassword)
    }

    fun onLoginButtonClicked() {
        val emailError = checkEmail(email.value.inputField)
        if (emailError) {
            return
        }
        viewModelScope.launch {
            loading.value = true
            val res = userRepository.loginUser(
                email = email.value.inputField,
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

    fun onRedirectToRegisterClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.REGISTER)
        }
    }

    fun loginWithGoogle(googleIdToken: String) {
        viewModelScope.launch {
            loading.value = true
            val res = userRepository.loginWithGoogle(googleIdToken)
            if (res.isSuccess) {
                navigationDispatcher.dispatchNavigationCommand { navController ->
                    navController.popBackStack()
                    navController.navigate(NavDest.HOME)
                }
            }
            loading.value = false
        }
    }
}

