package com.grdkrll.kfinance.model.dto.groups.response

@kotlinx.serialization.Serializable
class GroupResponse(
    val id: Int,
    val name: String,
    val handle: String
)