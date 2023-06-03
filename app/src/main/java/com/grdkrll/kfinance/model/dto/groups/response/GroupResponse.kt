package com.grdkrll.kfinance.model.dto.groups.response

import kotlinx.serialization.Serializable

/**
 * A Response class used to get data of the Groups
 *
 * @property id the id of the Group
 * @property name the name of the Group
 * @property handle the handle of the Group
 * @property ownerId the id from the backend database of the Owner of the Group
 */
@Serializable
class GroupResponse(
    val id: Int,
    val name: String,
    val handle: String,
    val ownerId: Int
)