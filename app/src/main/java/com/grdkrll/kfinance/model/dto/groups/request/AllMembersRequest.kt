package com.grdkrll.kfinance.model.dto.groups.request

import kotlinx.serialization.Serializable

/**
 * A Request class used to get all members of the Group
 *
 * @param handle the handle of the Group
 */
@Serializable
class AllMembersRequest(
    val handle: String
)