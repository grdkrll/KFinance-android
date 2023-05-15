package com.grdkrll.kfinance.model.dto.groups.request

import kotlinx.serialization.Serializable

@Serializable
class CreateGroupRequest(
    val name: String,
    val handle: String,
    val password: String
)