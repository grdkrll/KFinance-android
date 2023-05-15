package com.grdkrll.kfinance.model.dto.groups.request

import kotlinx.serialization.Serializable


@Serializable
class ChangeGroupRequest(
    val name: String,
    val handle: String,
    val password: String,
    val confirmPassword: String
)