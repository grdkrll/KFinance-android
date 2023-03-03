package com.grdkrll.kfinance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun RegisterScreen() {
    var handle by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            label = { Text("Handle") },
            value = handle,
            onValueChange = { handle = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            placeholder = { Text("Handle") }
        )
        OutlinedTextField(
            label = { Text("Email")},
            value = email,
            onValueChange = {email = it},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            placeholder = { Text("Email") }
        )
        OutlinedTextField(
            label = { Text("Password")},
            value = password,
            onValueChange = {password = it},
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = { Text("Password") },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(Icons.Filled.Lock, "Visibility")
                }
            }
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
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Register", style = MaterialTheme.typography.titleMedium)
        }
    }
}