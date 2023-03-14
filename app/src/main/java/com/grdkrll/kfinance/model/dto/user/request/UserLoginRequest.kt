package com.grdkrll.kfinance.model.dto.user.request

import kotlinx.serialization.Serializable

@Serializable
class UserLoginRequest(
    val login: String,
    val password: String
)