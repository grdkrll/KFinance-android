package com.grdkrll.kfinance.model.dto.groups.response

import kotlinx.serialization.Serializable

@Serializable
class MemberResponse(
    val name: String,
    val handle: String
)