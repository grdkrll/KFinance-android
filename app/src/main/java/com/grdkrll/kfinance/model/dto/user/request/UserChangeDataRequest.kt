package com.grdkrll.kfinance.model.dto.user.request

import kotlinx.serialization.Serializable

/**
 * A Request class used to change User data in the backend database
 *
 * @param name a new name for the User
 * @param handle a new handle for the User
 * @param email a new email for the User
 * @param password a new password for the User. Leave parameter empty to not change the password
 * @param confirmPassword the current User Password
 */
@Serializable
class UserChangeDataRequest(
    val name: String,
    val handle: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)