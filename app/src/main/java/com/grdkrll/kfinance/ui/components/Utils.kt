package com.grdkrll.kfinance.ui.components

import androidx.compose.ui.graphics.Color

/**
 * A function used to choose color for the Total Sum Number (if the number is negative color is #4D0000, #034931 otherwise)
 */
fun sumColorPicker(sum: Double): Color {
    return if (sum < 0) {
        Color(77, 0, 0)
    } else {
        Color(3, 73, 49)
    }
}