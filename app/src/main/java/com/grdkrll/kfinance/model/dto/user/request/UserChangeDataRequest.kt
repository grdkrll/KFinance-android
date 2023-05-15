package com.grdkrll.kfinance.model.dto.user.request

import kotlinx.serialization.Serializable

@Serializable
class UserChangeDataRequest(
    val name: String,
    val handle: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)