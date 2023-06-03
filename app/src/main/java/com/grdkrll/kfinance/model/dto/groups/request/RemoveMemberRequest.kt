package com.grdkrll.kfinance.model.dto.groups.request

import kotlinx.serialization.Serializable

/**
 * A Request class used to remove a User from the Group
 *
 * @param groupHandle the handle of the Group
 * @param userHandle the handle of the User
 */
@Serializable
class RemoveMemberRequest(
    val groupHandle: String,
    val userHandle: String
)