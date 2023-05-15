package com.grdkrll.kfinance.sealed

import com.grdkrll.kfinance.model.dto.groups.response.MemberResponse

sealed class MembersState {
    class Success(val data: List<MemberResponse>) : MembersState()
    class Failure(val message: String) : MembersState()
    object Loading: MembersState()
    object Empty: MembersState()
}
