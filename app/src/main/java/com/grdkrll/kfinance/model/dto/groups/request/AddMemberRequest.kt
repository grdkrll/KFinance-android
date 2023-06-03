package com.grdkrll.kfinance.model.dto.groups.request

import kotlinx.serialization.Serializable

/**
 * A Request class used to join into the Group
 *
 * @param handle the handle of the Group
 * @param password the password of the Group
 */
@Serializable
class AddMemberRequest(
    val handle: String,
    val password: String
)