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
import com.grdkrll.kfinance.model.Group
import com.grdkrll.kfinance.model.User
import com.grdkrll.kfinance.repository.UserRepository
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * A View Model Class for Profile Screen
 *
 * @property name the name currently held inside the Input Field
 * @property handle the handle currently held inside the Input Field
 * @property email the email currently held inside the Input Field
 * @property password the password currently held inside the Input Field
 * @property confirmPassword the password currently held inside the Input Field
 * @property loading indicates that an API call to Change User Data is in process
 */
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

    /**
     * Used to put basic information about the User inside the Input Fields
     */
    init {
        val userGroup = userRepository.getUser() ?: throw UserNotAuthenticatedException()
        val user = userGroup.first
        _name.value = name.value.copy(inputField = user.name)
        _handle.value = handle.value.copy(inputField = user.handle)
        _email.value = email.value.copy(inputField = user.email)
    }

    /**
     * Used to get data about the User
     *
     * @return if the User is not logged in returns null otherwise returns a pair of instances of [User] and [Group]
     */
    fun getUser() = userRepository.getUser()

    /**
     * Used to change [name] whenever value inside the Input Field changes
     */
    fun onNameChanged(newName: String) {
        _name.value = name.value.copy(inputField = newName)
    }

    /**
     * Used check and change [email] whenever value inside the Input Field changes (if the [email] is incorrect puts an error message into the Input Field)
     */
    fun onEmailChanged(newEmail: String) {
        _email.value = email.value.copy(
            inputField = newEmail,
            isError = checkEmail(newEmail),
            errorMessage = "Please, enter real email"
        )
    }

    /**
     * Used to check and change [handle] whenever value inside the Input Field changes (if the [handle] is incorrect puts an error message into the Input Field)
     */
    fun onHandleChanged(newHandle: String) {
        _handle.value = handle.value.copy(
            inputField = newHandle,
            isError = checkHandle(newHandle),
            errorMessage = "Please, only use letters, numbers and underscores in your handle"
        )
    }

    /**
     * Used to check and change [password] whenever value inside the Input Field changes (if the [password] is incorrect puts and error message into the Input Field)
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
     * Used to change [confirmPassword] whenever value inside the Input Field changes
     */
    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _confirmPassword.value = confirmPassword.value.copy(
            inputField = newConfirmPassword
        )
    }

    /**
     * Used to check that all the fields are correct, and if so makes an API call to the backend to change the User Data. If call is successful redirects to Home Screen, otherwise puts an error message into Input Field
     */
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
            return
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
                onRedirectToHomeClicked()
            } else {
                _confirmPassword.value =
                    confirmPassword.value.copy(isError = true, errorMessage = "Wrong password")
            }
            loading.value = false
        }
    }

    /**
     * Used to redirect to Home Screen
     */
    fun onRedirectToHomeClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.HOME)
        }
    }

    /**
     * Used to redirect to Groups List Screen
     */
    fun onRedirectToGroupsListClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.GROUPS_LIST)
        }
    }
}