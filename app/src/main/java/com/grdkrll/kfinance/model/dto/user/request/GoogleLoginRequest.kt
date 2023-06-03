package com.grdkrll.kfinance.model.dto.user.request

import kotlinx.serialization.Serializable

/**
 * A Request class used to Sign In or Sign Up through Google One Tap Sign-In
 *
 * @param googleIdToken the Token that was returned by Google Sign-In Intent
 */
@Serializable
class GoogleLoginRequest(
    val googleIdToken: String
)