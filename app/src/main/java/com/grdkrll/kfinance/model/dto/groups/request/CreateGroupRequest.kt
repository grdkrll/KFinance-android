package com.grdkrll.kfinance.model.dto.groups.request

@kotlinx.serialization.Serializable
class CreateGroupRequest(
    val name: String,
    val handle: String,
    val password: String
)