package com.grdkrll.kfinance.model.dto.groups.response

import kotlinx.serialization.Serializable

/**
 * A Response class used to hold information about a Member of a Group
 *
 * @property name the name of the Member
 * @property handle the handle of the Member
 */
@Serializable
class MemberResponse(
    val name: String,
    val handle: String
)