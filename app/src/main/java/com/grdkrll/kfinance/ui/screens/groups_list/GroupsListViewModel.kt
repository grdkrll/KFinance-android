package com.grdkrll.kfinance.ui.screens.groups_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigator
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.repository.GroupRepository
import com.grdkrll.kfinance.repository.GroupSettingsRepository
import com.grdkrll.kfinance.repository.SelectedGroupRepository
import com.grdkrll.kfinance.repository.UserRepository
import com.grdkrll.kfinance.sealed.GroupsState
import com.grdkrll.kfinance.ui.NavigationDispatcher
import kotlinx.coroutines.launch

class GroupsListViewModel(
    private val groupRepository: GroupRepository,
    private val navigationDispatcher: NavigationDispatcher,
    private val selectedGroupRepository: SelectedGroupRepository,
    private val userRepository: UserRepository,
    private val settingsRepository: GroupSettingsRepository
) : ViewModel() {

    val response: MutableState<GroupsState> = mutableStateOf(GroupsState.Empty)

    fun getUser() = userRepository.getUser()

    fun onSelectGroupClicked(groupId: Int, groupName: String) {
        selectedGroupRepository.saveGroup(groupId, groupName)
        redirectToHome()
    }

    fun onDeselectGroupClicked() {
        groupRepository.deselectGroup()
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.HOME)
        }
    }

    fun onCreateGroupButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.navigate(NavDest.CREATE_GROUP)
        }
    }

    fun fetchGroups() {
        response.value = GroupsState.Loading
        viewModelScope.launch {
            val res = groupRepository.getAllGroupsOfUser()
            if (res.isSuccess) {
                val list = res.getOrNull() ?: throw Exception()
                response.value = GroupsState.Success(list)
            }
        }
    }

    fun leaveGroup(handle: String) {

    }
    fun redirectToHome() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.HOME)
        }
    }

    fun redirectToProfile() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.PROFILE)
        }
    }

    fun redirectToGroupSettings(name: String, handle: String) {
        settingsRepository.selectGroup(name, handle)
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.navigate(NavDest.GROUP_SETTINGS)
        }
    }
}