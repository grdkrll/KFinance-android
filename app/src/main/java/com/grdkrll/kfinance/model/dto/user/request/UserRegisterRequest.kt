package com.grdkrll.kfinance.model.dto.user.request

import kotlinx.serialization.Serializable

/**
 * A Request class used to Sing Up User
 *
 * @param name the name of the User
 * @param email the email of the User
 * @param handle the handle of the User
 * @param password the password of the User
 */
@Serializable
class UserRegisterRequest(
    val name: String,
    val email: String,
    val handle: String,
    val password: String
)