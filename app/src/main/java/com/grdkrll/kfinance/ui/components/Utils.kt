package com.grdkrll.kfinance.ui.components

import androidx.compose.ui.graphics.Color

fun sumColorPicker(sum: Double): Color {
    return if (sum < 0) {
        Color(77, 0, 0)
    } else {
        Color(3, 73, 49)
    }
}