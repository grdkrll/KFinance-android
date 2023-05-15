package com.grdkrll.kfinance.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.model.Group
import com.grdkrll.kfinance.model.User
import com.grdkrll.kfinance.ui.components.buttons.CloseButton

@Composable
fun TopBar(user: User, group: Group, onDeselectGroup: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(0),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        if (group.id == -1) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    user.handle,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            Row {
                Text(group.name, modifier = Modifier.padding(16.dp))
                Spacer(modifier = Modifier.weight(1f))
                CloseButton(onDeselectGroup)
            }
        }
    }
}