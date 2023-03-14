package com.grdkrll.kfinance.model.dto.user.response

import kotlinx.serialization.Serializable

@Serializable
class UserResponse(
    val token: String,
    val email: String,
    val handle: String
)