package com.grdkrll.kfinance.ui.components.input_fields

/**
 * A Data Class used for Stateful Input Fields
 */
data class InputField (
    var inputField: String = "",
    var isError: Boolean = false,
    var errorMessage: String = ""
)