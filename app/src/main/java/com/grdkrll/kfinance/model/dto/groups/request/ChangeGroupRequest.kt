package com.grdkrll.kfinance.model.dto.groups.request

import kotlinx.serialization.Serializable

/**
 * A Request class used to change the data of the Group
 *
 * @param name a new name for the Group
 * @param handle a new handle for the Group
 * @param password a new password for the Group (leave empty to leave an old one)
 * @param confirmPassword an old password of the Group
 */
@Serializable
class ChangeGroupRequest(
    val name: String,
    val handle: String,
    val password: String,
    val confirmPassword: String
)