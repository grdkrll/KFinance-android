package com.grdkrll.kfinance.ui.screens.login

import EmailInputField
import PasswordInputField
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.google.android.gms.common.api.ApiException
import com.grdkrll.kfinance.model.contracts.GoogleApiContract
import com.grdkrll.kfinance.ui.components.SimpleCircularProgressIndicator
import com.grdkrll.kfinance.ui.components.SimpleInputField
import com.grdkrll.kfinance.ui.components.buttons.GoogleSignInButton
import com.grdkrll.kfinance.ui.components.buttons.SimpleButton
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoginScreen(
    viewModel: LoginViewModel
) {
    val authResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleApiContract()) { task ->
            try {
                val gsa = task?.getResult(ApiException::class.java)

                if (gsa != null) {
                    viewModel.loginWithGoogle(gsa.idToken ?: throw Exception())
                }
            } catch (e: ApiException) {
                Log.d("Error in AuthScreen%s", e.toString())
            }
        }
    Scaffold { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (viewModel.loading.value) {
                false -> {
                    LoginBox(
                        emailStateFlow = viewModel.email,
                        onEmailChanged = viewModel::onEmailChanged,
                        passwordStateFlow = viewModel.password,
                        onPasswordChanged = viewModel::onPasswordChanged
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SimpleButton(text = "Sign In", onClicked = viewModel::onLoginButtonClicked)
                    Spacer(modifier = Modifier.height(48.dp))

                    GoogleSignInButton { authResultLauncher.launch(1); }
                    Spacer(modifier = Modifier.weight(0.5f))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Don't have an account?")
                        ClickableText(
                            text = AnnotatedString("Sign Up"),
                            style = TextStyle(color = Color(217, 203, 79)),
                            onClick = { viewModel.onRedirectToRegisterClicked() },
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
fun LoginBox(
    emailStateFlow: StateFlow<InputField>,
    onEmailChanged: (String) -> Unit,
    passwordStateFlow: StateFlow<InputField>,
    onPasswordChanged: (String) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            Color(197, 183, 134)
        ),
        modifier = Modifier.padding(24.dp)
    ) {
        SimpleInputField(
            text = "Email:",
            valueStateFlow = emailStateFlow,
            onValueChanged = onEmailChanged
        )
        SimpleInputField(
            text = "Password:",
            valueStateFlow = passwordStateFlow,
            onValueChanged = onPasswordChanged,
            keyboardType = KeyboardType.Password,
            passwordType = true
        )
    }
}