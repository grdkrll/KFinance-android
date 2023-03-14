package com.grdkrll.kfinance.ui.screens.login

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
        EmailInputField(
            emailField = viewModel.email,
            onEmailChanged = viewModel::onEmailChanged
        )
        PasswordInputField(
            passwordField = viewModel.password,
            onPasswordChange = viewModel::onPasswordChanged
        )
        LoginButton(viewModel)
        Text("OR", modifier = Modifier.padding(16.dp))
        RegisterButton(viewModel)
    }
}

@Composable
fun LoginButton(
    viewModel: LoginViewModel
) {
    Button(
        onClick = viewModel::onLoginButtonClicked,
        shape = MaterialTheme.shapes.extraSmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(8.dp)
    ) {
        Text("Login")
    }
}

@Composable
fun RegisterButton(
    viewModel: LoginViewModel
) {
    Button(
        onClick = viewModel::onRedirectToRegisterClicked,
        shape = MaterialTheme.shapes.extraSmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(8.dp)
    ) {
        Text("Register")
    }
}