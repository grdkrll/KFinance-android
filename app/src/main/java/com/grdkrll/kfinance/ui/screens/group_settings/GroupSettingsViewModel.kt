package com.grdkrll.kfinance.ui.screens.group_settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.checkHandle
import com.grdkrll.kfinance.checkPassword
import com.grdkrll.kfinance.repository.GroupRepository
import com.grdkrll.kfinance.repository.GroupSettingsRepository
import com.grdkrll.kfinance.repository.SelectedGroupRepository
import com.grdkrll.kfinance.sealed.MembersState
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    init {
        val (savedName, savedHandle) = settingsRepository.fetchGroupInfo()
        if (savedName == null || savedHandle == null) {
            throw Exception()
        }
        _name.value = name.value.copy(inputField = savedName)
        _handle.value = handle.value.copy(inputField = savedHandle)

    }

    fun onNameChanged(newName: String) {
        _name.value = name.value.copy(inputField = newName)
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
        _confirmPassword.value = confirmPassword.value.copy(inputField = newConfirmPassword)
    }

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

    fun onCloseButtonClicked() {
        settingsRepository.deselectGroup()
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.GROUPS_LIST)
        }
    }

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