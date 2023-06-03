package com.grdkrll.kfinance.ui.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A Composable Function used to make a Button
 */
@Composable
fun SimpleButton(
    text: String,
    onClicked: () -> Unit
) {
    Button(
        onClick = onClicked,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(Color(217, 203, 79)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text,
            style = TextStyle(color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 16.sp),
            modifier = Modifier.padding(4.dp)
        )
    }
}