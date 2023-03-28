package com.grdkrll.kfinance.ui.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.R

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_google),
                contentDescription = stringResource(id = R.string.google_sign_in_button),
                contentScale = ContentScale.FillHeight
            )
            Text("Google Sign-In", color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.fillMaxWidth())
        }
    }
}