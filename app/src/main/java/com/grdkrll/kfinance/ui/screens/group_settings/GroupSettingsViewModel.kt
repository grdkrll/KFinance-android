package com.grdkrll.kfinance.ui.screens.group_settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.checkHandle
import com.grdkrll.kfinance.checkPassword
import com.grdkrll.kfinance.model.dto.groups.response.MemberResponse
import com.grdkrll.kfinance.repository.GroupRepository
import com.grdkrll.kfinance.repository.GroupSettingsRepository
import com.grdkrll.kfinance.sealed.MembersState
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * A View Model Class for Group Setting Screen
 *
 * @property response indicates whether the List of Members was fetched or not. Holds the List of [MemberResponse] when it's fetched
 * @property name the name that is currently held in the Input Field
 * @property handle the handle that is currently held in the Input Field
 * @property password the password that is currently held in the Input Field
 * @property confirmPassword the password that is currently held in the Input Field
 */
class GroupSettingsViewModel(
    private val navigationDispatcher: NavigationDispatcher,
    private val settingsRepository: GroupSettingsRepository,
    private val groupRepository: GroupRepository
) : ViewModel() {

    val response: MutableState<MembersState> = mutableStateOf(MembersState.Empty)

    private val _name = MutableStateFlow(InputField())
    val name: StateFlow<InputField> = _name

    private val _handle = MutableStateFlow(InputField())
    val handle: StateFlow<InputField> = _handle

    private val _password = MutableStateFlow(InputField())
    val password: StateFlow<InputField> = _password

    private val _confirmPassword = MutableStateFlow(InputField())
    val confirmPassword: StateFlow<InputField> = _confirmPassword

    /**
     * Used to put basic information into Input Fields
     */
    init {
        val (savedName, savedHandle) = settingsRepository.fetchGroupInfo()
        if (savedName == null || savedHandle == null) {
            throw Exception()
        }
        _name.value = name.value.copy(inputField = savedName)
        _handle.value = handle.value.copy(inputField = savedHandle)

    }

    /**
     * Used to change [name] whenever value inside the Input Field changes
     */
    fun onNameChanged(newName: String) {
        _name.value = name.value.copy(inputField = newName)
    }

    /**
     * Used to change [handle] whenever value inside the Input Field changes (if the handle is incorrect, puts an error message into the Input Field)
     */
    fun onHandleChanged(newHandle: String) {
        _handle.value = handle.value.copy(
            inputField = newHandle,
            isError = checkHandle(newHandle),
            errorMessage = "Please, only use letters, numbers and underscores in your handle"
        )
    }

    /**
     * Used to change [password] whenever value inside the Input Field changes (if the password is incorrect, puts an error message into the Input Field)
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
        _confirmPassword.value = confirmPassword.value.copy(inputField = newConfirmPassword)
    }

    /**
     * Used to check that all fields are correct, and if so makes an API call to the backend to change data about the Group (if the [confirmPassword] value is incorrect stays on the Screen and puts an error message into the Input Field, otherwise redirects to Groups List Screen)
     */
    fun onChangeGroupButtonClicked() {
        val passwordError = if(password.value.inputField.isEmpty()) null else checkPassword(password.value.inputField)
        _password.value = password.value.copy(
            isError = passwordError != null,
            errorMessage = passwordError ?: ""
        )
        val handleError = checkHandle(handle.value.inputField)
        _handle.value = handle.value.copy(
            isError = handleError,
            errorMessage = "Please, only use letters, numbers and underscores in your handle"
        )
        if (passwordError != null || handleError) {
            return
        }
        viewModelScope.launch {
            val res = groupRepository.changeGroup(
                name.value.inputField,
                handle.value.inputField,
                password.value.inputField,
                confirmPassword.value.inputField
            )
            if (res.isSuccess) {
                navigationDispatcher.dispatchNavigationCommand { navController ->
                    navController.popBackStack()
                    navController.navigate(NavDest.GROUPS_LIST)
                }
            }
        }
    }

    /**
     * Used to close the Group Settings Screen
     */
    fun onCloseButtonClicked() {
        settingsRepository.deselectGroup()
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.GROUPS_LIST)
        }
    }

    /**
     * Used to make an API call to the backend to fetch a List of all members of the Group
     */
    fun fetchMembers() {
        response.value = MembersState.Loading
        viewModelScope.launch {
            val res = groupRepository.getAllMembers(
                settingsRepository.fetchGroupInfo().second ?: throw Exception("Group not found")
            )
            if (res.isSuccess) {
                val list = res.getOrNull() ?: throw Exception("Not success on get all members")
                response.value = MembersState.Success(list)
            }
        }
    }

    /**
     * Used to make an API call to the backend to remove a member from the Group
     */
    fun removeMember(userHandle: String) {
        viewModelScope.launch {
            val res = groupRepository.removeMember(
                settingsRepository.fetchGroupInfo().second ?: throw Exception("Group not found"),
                userHandle
            )
            if (res.isSuccess) {
                fetchMembers()
            }
        }
    }
}