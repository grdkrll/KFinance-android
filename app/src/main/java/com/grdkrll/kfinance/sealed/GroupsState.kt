package com.grdkrll.kfinance.sealed

import com.grdkrll.kfinance.model.dto.groups.response.GroupResponse

/**
 * A Sealed State Class Used to load a List of Groups of the User
 */
sealed class GroupsState {
    class Success(val data: List<GroupResponse>) : GroupsState()
    class Failure(val message: String) : GroupsState()
    object Loading: GroupsState()
    object Empty: GroupsState()
}