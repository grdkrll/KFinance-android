package com.grdkrll.kfinance.model.dto.user.response

import kotlinx.serialization.Serializable

/**
 * A Response class that is returned whenever any User related API call is made
 *
 * @property token A JWT token used to authorize API calls to the backend
 * @property id the id of the User in the backend database
 * @property name the name of the User in the backend database
 * @property email the email of the User in the backend database
 * @property handle the handle of the User in the backend database
 */
@Serializable
class UserResponse(
    val token: String,
    val id: Int,
    val name: String,
    val email: String,
    val handle: String
)