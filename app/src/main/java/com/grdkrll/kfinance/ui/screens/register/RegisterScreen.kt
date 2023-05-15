package com.grdkrll.kfinance.ui.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.ui.components.SimpleCircularProgressIndicator
import com.grdkrll.kfinance.ui.components.SimpleInputField
import com.grdkrll.kfinance.ui.components.buttons.SimpleButton
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (viewModel.loading.value) {
                false -> {
                    RegisterBox(
                        nameStateFlow = viewModel.name,
                        onNameChanged = viewModel::onNameChanged,
                        emailStateFlow = viewModel.email,
                        onEmailChanged = viewModel::onEmailChanged,
                        handleStateFlow = viewModel.handle,
                        onHandleChanged = viewModel::onHandleChanged,
                        passwordStateFlow = viewModel.password,
                        onPasswordChanged = viewModel::onPasswordChanged,
                        confirmPasswordStateFlow = viewModel.passwordConfirm,
                        onConfirmPasswordChanged = viewModel::onPasswordConfirmChanged
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SimpleButton(text = "Sign Up", onClicked = viewModel::onRegisterButtonClicked)
                    Spacer(modifier = Modifier.weight(1.0f))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Already have an account?")
                        ClickableText(
                            text = AnnotatedString("Sign In"),
                            style = TextStyle(color = Color(217, 203, 79)),
                            onClick = { viewModel.onRedirectToLoginClicked() },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }

                true -> SimpleCircularProgressIndicator()
            }
        }
    }
}

@Composable
fun RegisterBox(
    nameStateFlow: StateFlow<InputField>,
    onNameChanged: (String) -> Unit,
    emailStateFlow: StateFlow<InputField>,
    onEmailChanged: (String) -> Unit,
    handleStateFlow: StateFlow<InputField>,
    onHandleChanged: (String) -> Unit,
    passwordStateFlow: StateFlow<InputField>,
    onPasswordChanged: (String) -> Unit,
    confirmPasswordStateFlow: StateFlow<InputField>,
    onConfirmPasswordChanged: (String) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            Color(197, 183, 134)
        ),
        modifier = Modifier.padding(24.dp)
    ) {
        SimpleInputField(
            text = "Name:",
            valueStateFlow = nameStateFlow,
            onValueChanged = onNameChanged
        )
        SimpleInputField(
            text = "Email:",
            valueStateFlow = emailStateFlow,
            onValueChanged = onEmailChanged
        )
        SimpleInputField(
            text = "Handle:",
            valueStateFlow = handleStateFlow,
            onValueChanged = onHandleChanged
        )
        SimpleInputField(
            text = "Password:",
            valueStateFlow = passwordStateFlow,
            onValueChanged = onPasswordChanged,
            keyboardType = KeyboardType.Password,
            passwordType = true
        )
        SimpleInputField(
            text = "Confirm password:",
            valueStateFlow = confirmPasswordStateFlow,
            onValueChanged = onConfirmPasswordChanged,
            keyboardType = KeyboardType.Password,
            passwordType = true
        )
    }
}
