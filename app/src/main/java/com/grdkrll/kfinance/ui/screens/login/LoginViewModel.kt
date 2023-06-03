package com.grdkrll.kfinance.ui.screens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.checkEmail
import com.grdkrll.kfinance.repository.UserRepository
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * A View Model Class for Login Screen
 *
 * @property email the email that is currently held inside the Input Field
 * @property password the password that is currently held inside the Input Field
 * @property loading indicates that an API call to Sign In the User is in process
 *
 */
class LoginViewModel(
    private val navigationDispatcher: NavigationDispatcher,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _email = MutableStateFlow(InputField())
    val email: StateFlow<InputField> = _email

    private val _password = MutableStateFlow(InputField())
    val password: StateFlow<InputField> = _password

    val loading: MutableState<Boolean> = mutableStateOf(false)

    /**
     * Used to check and change [email] whenever value inside Input Field changes (if the value of [email] is incorrect puts an error message into Input Field)
     */
    fun onEmailChanged(newEmail: String) {
        _email.value = email.value.copy(
            inputField = newEmail,
            isError = checkEmail(newEmail),
            errorMessage = "Please, enter real email"
        )
    }

    /**
     * Used to change [password] whenever value inside the Input Field changes
     */
    fun onPasswordChanged(newPassword: String) {
        _password.value = password.value.copy(inputField = newPassword)
    }

    /**
     * Used to check that all data fields are correct and if so make an API call to the backend to Sign In the User (if the Sign In is successful redirects to Home Screen, otherwise does nothing)
     */
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

    /**
     * Used to redirect to Register Screen
     */
    fun onRedirectToRegisterClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.REGISTER)
        }
    }

    /**
     * Used to make call upon an Intent to Sign In via Google One Tap Auth and if successful makes an API call to the backend to check that the Google Id Token returned by the Intent is correct or not. If successful redirects to home, otherwise does nothing
     */
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

