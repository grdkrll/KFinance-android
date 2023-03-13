package com.grdkrll.kfinance.ui.screens.register

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.model.User
import com.grdkrll.kfinance.model.responce.user.UserResponce
import com.grdkrll.kfinance.repository.service.user.UserService
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val userService: UserService
) : ViewModel() {
    private val _email = MutableStateFlow(InputField())
    val email: StateFlow<InputField> = _email

    private val _password = MutableStateFlow(InputField())
    val password: StateFlow<InputField> = _password

    private val _passwordConfirm = MutableStateFlow(InputField())
    val passwordConfirm: StateFlow<InputField> = _passwordConfirm

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

    fun onPasswordConfirmChanged(newPasswordValue: String) {
        val passwordsMatch = (password.value.inputField == newPasswordValue)
        _passwordConfirm.value = passwordConfirm.value.copy(
            inputField = newPasswordValue,
            isError = !passwordsMatch,
            errorMessage = if(passwordsMatch) "" else "Passwords do not match"
        )
    }

    fun onRegisterButtonClicked() {
        runBlocking {
            launch {
                registerUser(email.value.inputField, password.value.inputField)
            }
        }
    }

    fun onRedirectToLoginClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.LOGIN)
        }
    }

    private suspend fun registerUser(email: String, password: String) {
        _loading.value = true
        val res: UserResponce? = userService.registerUser(email, password)
        _loading.value = false
        if(res != null) {
            navigationDispatcher.dispatchNavigationCommand { navController ->
                navController.popBackStack()
                navController.navigate(NavDest.HOME)
            }
        } else {
            _email.value = this.email.value.copy(isError = true, errorMessage = "Wrong email or password")
        }
    }

}