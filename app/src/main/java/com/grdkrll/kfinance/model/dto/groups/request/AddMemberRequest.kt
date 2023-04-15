package com.grdkrll.kfinance.model.dto.groups.request

@kotlinx.serialization.Serializable
class AddMemberRequest(
    val handle: String,
    val password: String
)