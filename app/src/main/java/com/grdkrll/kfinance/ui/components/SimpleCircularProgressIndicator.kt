package com.grdkrll.kfinance.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A Composable Functions used to display Circular Progress Indicator
 */
@Composable
fun SimpleCircularProgressIndicator() {
    return CircularProgressIndicator(
        color = Color(217, 203, 79),
        modifier = Modifier.padding(16.dp)
    )
}