package com.grdkrll.kfinance.model.dto.groups.request

import kotlinx.serialization.Serializable

/**
 * A Request class used to create a new Group
 *
 * @param name the name of the Group
 * @param handle the handle of the Group
 * @param password the password of the Group
 */
@Serializable
class CreateGroupRequest(
    val name: String,
    val handle: String,
    val password: String
)