package com.grdkrll.kfinance.model.dto.user.request

import kotlinx.serialization.Serializable

@Serializable
class UserRegisterRequest(
    val name: String,
    val email: String,
    val handle: String,
    val password: String
)