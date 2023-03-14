package com.grdkrll.kfinance.ui.screens.register

import EmailInputField
import PasswordInputField
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel
) {
    RegisterBox(viewModel = viewModel)
}

@Composable
fun RegisterBox(
    viewModel: RegisterViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailInputField(
            emailField = viewModel.email,
            onEmailChanged = viewModel::onEmailChanged
        )
        PasswordInputField(
            passwordField = viewModel.password,
            onPasswordChange = viewModel::onPasswordChanged
        )
        PasswordInputField(
            passwordField = viewModel.passwordConfirm,
            onPasswordChange = viewModel::onPasswordConfirmChanged,
            passwordLabel = "Confirm Password"
        )
        RegisterButton(viewModel)
        Text("OR", modifier = Modifier.padding(16.dp))
        LoginButton(viewModel)
    }
}

@Composable
fun RegisterButton(
    viewModel: RegisterViewModel
) {
    Button(
        onClick = viewModel::onRegisterButtonClicked,
        shape = MaterialTheme.shapes.extraSmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(8.dp)
    ) {
        Text("Register")
    }
}

@Composable
fun LoginButton(
    viewModel: RegisterViewModel
) {
    Button(
        onClick = viewModel::onRedirectToLoginClicked,
        shape = MaterialTheme.shapes.extraSmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(8.dp)
    ) {
        Text("Login")
    }
}