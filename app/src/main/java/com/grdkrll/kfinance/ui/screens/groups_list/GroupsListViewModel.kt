package com.grdkrll.kfinance.ui.screens.groups_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.model.Group
import com.grdkrll.kfinance.model.User
import com.grdkrll.kfinance.model.dto.groups.response.GroupResponse
import com.grdkrll.kfinance.repository.GroupRepository
import com.grdkrll.kfinance.repository.GroupSettingsRepository
import com.grdkrll.kfinance.repository.SelectedGroupRepository
import com.grdkrll.kfinance.repository.UserRepository
import com.grdkrll.kfinance.sealed.GroupsState
import com.grdkrll.kfinance.ui.NavigationDispatcher
import kotlinx.coroutines.launch

/**
 * A View Model Class for Groups List Screen
 *
 * @property response indicates whether the List of Groups was fetched or not. When fetched holds a List of [GroupResponse]
 */
class GroupsListViewModel(
    private val groupRepository: GroupRepository,
    private val navigationDispatcher: NavigationDispatcher,
    private val selectedGroupRepository: SelectedGroupRepository,
    private val userRepository: UserRepository,
    private val settingsRepository: GroupSettingsRepository
) : ViewModel() {

    val response: MutableState<GroupsState> = mutableStateOf(GroupsState.Empty)

    /**
     * Used to get data about the User and the Selected Group
     *
     * @return if User is not Signed In returns null, otherwise returns a Pair of [User], [Group] ([Group] id will be set to -1, if no Group was selected)
     */
    fun getUser() = userRepository.getUser()

    /**
     * Used to select a Group
     *
     * @param groupId the id of the Selected Group
     * @param groupName the name of the Selected Group
     */
    fun onSelectGroupClicked(groupId: Int, groupName: String) {
        selectedGroupRepository.saveGroup(groupId, groupName)
        redirectToHome()
    }

    /**
     * Used to deselect the Selected Group
     */
    fun onDeselectGroupClicked() {
        groupRepository.deselectGroup()
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.HOME)
        }
    }

    /**
     * Used to redirect to Create Group Screen
     */
    fun onCreateGroupButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.navigate(NavDest.CREATE_GROUP)
        }
    }

    /**
     * Used to make an API call to the backend to get a List of all Groups of the User
     */
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

    /**
     * Used to redirect to Home Screen
     */
    fun redirectToHome() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.HOME)
        }
    }

    /**
     * Used to redirect to Profile Screen
     */
    fun redirectToProfile() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.PROFILE)
        }
    }

    /**
     * Used to redirect to Group Settings Screen
     *
     * @param name the name of the Group to be modified
     * @param handle the handle of the Group to be modified
     */
    fun redirectToGroupSettings(name: String, handle: String) {
        settingsRepository.selectGroup(name, handle)
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.navigate(NavDest.GROUP_SETTINGS)
        }
    }
}