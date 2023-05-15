package com.grdkrll.kfinance.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SimpleFloatingActionButton(
    onClicked: () -> Unit,
    contentDescription: String? = null
) {
    FloatingActionButton(
        onClick = onClicked,
        modifier = Modifier
            .padding(16.dp)
            .padding(8.dp),
        containerColor = Color(217, 203, 79)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription)
    }
}
