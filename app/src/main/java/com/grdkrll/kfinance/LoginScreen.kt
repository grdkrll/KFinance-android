package com.grdkrll.kfinance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            label = { Text("Login") },
            value = login,
            onValueChange = { login = it },
            placeholder = { Text("Login") }
        )
        OutlinedTextField(
            label = { Text("Password")},
            value = password,
            onValueChange = {password = it},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = { Text("Password") }
        )
        ClickableText(
            text = AnnotatedString("Forgot your password?"),
            onClick = {},
            style = MaterialTheme.typography.labelLarge.merge(TextStyle(color = MaterialTheme.colorScheme.onSurface)),
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .align(Alignment.End)
        )
        OutlinedButton(
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Login", style = MaterialTheme.typography.titleMedium)
        }
        Text(text = "OR", modifier = Modifier.padding(16.dp))
        OutlinedButton(
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            onClick = onNavigateToRegister
        ) {
            Text(text = "Register", style = MaterialTheme.typography.titleMedium)
        }
    }
}