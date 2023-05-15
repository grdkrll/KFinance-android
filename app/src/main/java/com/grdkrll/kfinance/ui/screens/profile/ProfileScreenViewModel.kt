package com.grdkrll.kfinance.ui.screens.profile

import android.security.keystore.UserNotAuthenticatedException
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
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val userRepository: UserRepository,
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {
    private val _name = MutableStateFlow(InputField())
    val name: StateFlow<InputField> = _name

    private val _handle = MutableStateFlow(InputField())
    val handle: StateFlow<InputField> = _handle

    private val _email = MutableStateFlow(InputField())
    val email: StateFlow<InputField> = _email

    private val _password = MutableStateFlow(InputField())
    val password: StateFlow<InputField> = _password

    private val _confirmPassword = MutableStateFlow(InputField())
    val confirmPassword: StateFlow<InputField> = _confirmPassword

    val loading: MutableState<Boolean> = mutableStateOf(false)

    init {
        val userGroup = userRepository.getUser() ?: throw UserNotAuthenticatedException()
        val (user, group) = userGroup
        _name.value = name.value.copy(inputField = user.name)
        _handle.value = handle.value.copy(inputField = user.handle)
        _email.value = email.value.copy(inputField = user.email)
    }

    fun getUser() = userRepository.getUser()

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

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _confirmPassword.value = confirmPassword.value.copy(
            inputField = newConfirmPassword
        )
    }

    fun onConfirmButtonClicked() {
        val passwordError =
            if (password.value.inputField.isEmpty()) null else checkPassword(password.value.inputField)
        _password.value = password.value.copy(
            isError = passwordError != null,
            errorMessage = passwordError ?: ""
        )
        val emailError = checkEmail(email.value.inputField)

        _email.value = email.value.copy(
            isError = emailError,
            errorMessage = "Please, only use letters, numbers and underscores in your handle"
        )
        val handleError = checkHandle(handle.value.inputField)
        _handle.value = handle.value.copy(
            isError = handleError,
            errorMessage = "Please, only use letters, numbers and underscores in your handle"
        )
        if (passwordError != null || emailError || handleError) {
            return;
        }
        viewModelScope.launch {
            loading.value = true
            val res = userRepository.changeUserData(
                name = name.value.inputField,
                handle = handle.value.inputField,
                email = email.value.inputField,
                password = password.value.inputField,
                confirmPassword = confirmPassword.value.inputField
            )
            if (res.isSuccess) {
                navigationDispatcher.dispatchNavigationCommand { navController ->
                    navController.popBackStack()
                    navController.navigate(NavDest.HOME)
                }
            } else {
                _confirmPassword.value =
                    confirmPassword.value.copy(isError = true, errorMessage = "Wrong password")
            }
            loading.value = false
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