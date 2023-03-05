package com.grdkrll.kfinance.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.grdkrll.kfinance.R
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoginScreen(
    viewModel: LoginViewModel
) {
    LoginBox(viewModel = viewModel)
}
@Composable
fun LoginBox(
    viewModel: LoginViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(viewModel.email, viewModel::onEmailChanged)
    }
}

@Composable
fun EmailField(
    emailField: StateFlow<String>,
    onEmailChanged: (String) -> Unit
) {
    val email = emailField.collectAsState()
    OutlinedTextField(
        label = { Text("Email") },
        value = email.value,
        onValueChange = onEmailChanged,
        leadingIcon = { Icon(
            imageVector = Icons.Filled.Email,
            contentDescription = stringResource(id = R.string.login_email)
        ) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )
}