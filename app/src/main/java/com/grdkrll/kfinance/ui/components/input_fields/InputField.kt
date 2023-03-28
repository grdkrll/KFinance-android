package com.grdkrll.kfinance.ui.components.input_fields

data class InputField (
    var inputField: String = "",
    var isError: Boolean = false,
    var errorMessage: String = ""
)

data class SumInputField (
    var inputField: Double = 0.0,
    var isError: Boolean = false,
    var errorMessage: String = ""
)