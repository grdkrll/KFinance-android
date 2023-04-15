package com.grdkrll.kfinance.sealed

import com.grdkrll.kfinance.model.dto.groups.response.GroupResponse

sealed class GroupsState {
    class Success(val data: List<GroupResponse>) : GroupsState()
    class Failure(val message: String) : GroupsState()
    object Loading: GroupsState()
    object Empty: GroupsState()
}