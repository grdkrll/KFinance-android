package com.grdkrll.kfinance.model.dto.transaction.response

import kotlinx.serialization.Serializable

/**
 * A Response class used to get the total sum of transactions of the User in the backend database
 *
 * @property total the total sum of transactions of the User in the backend database
 */
@Serializable
class TotalResponse(
    val total: Double
)