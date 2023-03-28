package com.grdkrll.kfinance.model.dto.user.request

import kotlinx.serialization.Serializable

@Serializable
class GoogleLoginRequest(
    val googleIdToken: String
)