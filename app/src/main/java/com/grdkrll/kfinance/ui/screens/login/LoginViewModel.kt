package com.grdkrll.kfinance.ui.screens.login

import androidx.lifecycle.ViewModel
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {
    private val _email = MutableStateFlow(InputField())
    val email: StateFlow<InputField> = _email

    private val _password = MutableStateFlow(InputField())
    val password: StateFlow<InputField> = _password

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun onEmailChanged(newEmailValue: String) {
        _email.value = email.value.copy(
            inputField = newEmailValue,
            isError = false,
            errorMessage = ""
        )
    }

    fun onPasswordChanged(newPasswordValue: String) {
        _password.value = password.value.copy(
            inputField = newPasswordValue,
            isError = false,
            errorMessage = ""
        )
    }

    fun onRedirectToRegisterClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.REGISTER)
        }
    }

    private fun loginUser(email: String, password: String) {
        _loading.value = true
    }
}