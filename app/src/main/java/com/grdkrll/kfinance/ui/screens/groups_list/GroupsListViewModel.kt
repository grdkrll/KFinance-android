package com.grdkrll.kfinance.ui.screens.groups_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.repository.GroupRepository
import com.grdkrll.kfinance.repository.SelectedGroupRepository
import com.grdkrll.kfinance.sealed.GroupsState
import com.grdkrll.kfinance.ui.NavigationDispatcher
import kotlinx.coroutines.launch

class GroupsListViewModel(
    private val groupRepository: GroupRepository,
    private val navigationDispatcher: NavigationDispatcher,
    private val selectedGroupRepository: SelectedGroupRepository
) : ViewModel() {

    val response: MutableState<GroupsState> = mutableStateOf(GroupsState.Empty)

    fun onSelectGroupClicked(groupId: Int, groupName: String) {
        selectedGroupRepository.saveGroup(groupId, groupName)
        redirectToHome()
    }

    fun onAddGroupButtonClicked() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.navigate(NavDest.ADD_GROUP)
        }
    }

    fun fetchGroups() {
        response.value = GroupsState.Loading
        viewModelScope.launch {
            val res = groupRepository.getAllGroupsOfUser()
            if(res.isSuccess) {
                val list = res.getOrNull() ?: throw Exception()
                response.value = GroupsState.Success(list)
            }
        }
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
}