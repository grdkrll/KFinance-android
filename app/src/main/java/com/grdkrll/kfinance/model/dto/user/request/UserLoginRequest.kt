package com.grdkrll.kfinance.model.dto.user.request

import kotlinx.serialization.Serializable

/**
 * A Request class used to Sign In User
 * @param login a string that holds either User Email or User Handle
 * @param password the password of the User Account
 */
@Serializable
class UserLoginRequest(
    val login: String,
    val password: String
)