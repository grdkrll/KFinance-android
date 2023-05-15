package com.grdkrll.kfinance.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grdkrll.kfinance.ui.components.BottomNavigationBar
import com.grdkrll.kfinance.ui.components.SimpleCircularProgressIndicator
import com.grdkrll.kfinance.ui.components.SimpleInputField
import com.grdkrll.kfinance.ui.components.SimpleInputFieldWithCard
import com.grdkrll.kfinance.ui.components.buttons.SimpleButton
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = koinViewModel()
) {
    val userGroup = viewModel.getUser() ?: throw Exception("User not found")
    val (user, group) = userGroup
    val scrollState = rememberScrollState()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                listOf(false, false, true),
                listOf(
                    viewModel::onRedirectToHomeClicked,
                    viewModel::onRedirectToGroupsListClicked,
                    {})
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState, true)
        ) {
            when (viewModel.loading.value) {
                false -> {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            "Good Morning,",
                            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        )
                        Text(
                            user.name,
                            style = TextStyle(
                                color = Color(217, 203, 79),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(150.dp))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ProfileBox(
                            viewModel.name,
                            viewModel::onNameChanged,
                            viewModel.handle,
                            viewModel::onHandleChanged,
                            viewModel.email,
                            viewModel::onEmailChanged,
                            viewModel.password,
                            viewModel::onPasswordChanged
                        )
                        SimpleInputFieldWithCard(
                            text = "Confirm Password",
                            valueStateFlow = viewModel.confirmPassword,
                            onValueChanged = viewModel::onConfirmPasswordChanged,
                            keyboardType = KeyboardType.Password,
                            passwordType = true
                        )
                        SimpleButton(
                            text = "Confirm Changes",
                            onClicked = viewModel::onConfirmButtonClicked
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                true -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    SimpleCircularProgressIndicator()
                }

            }
        }
    }
}

@Composable
fun ProfileBox(
    nameStateFlow: StateFlow<InputField>,
    onNameChanged: (String) -> Unit,
    handleStateFlow: StateFlow<InputField>,
    onHandleChanged: (String) -> Unit,
    emailStateFlow: StateFlow<InputField>,
    onEmailChanged: (String) -> Unit,
    passwordStateFlow: StateFlow<InputField>,
    onPasswordChanged: (String) -> Unit,

    ) {
    androidx.compose.material3.Card(
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            Color(197, 183, 134)
        ),
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleInputField(
                text = "Your Name:",
                valueStateFlow = nameStateFlow,
                onValueChanged = onNameChanged
            )
            SimpleInputField(
                text = "Your Email:",
                valueStateFlow = emailStateFlow,
                onValueChanged = onEmailChanged,
                keyboardType = KeyboardType.Email
            )
            SimpleInputField(
                text = "Your Handle:",
                valueStateFlow = handleStateFlow,
                onValueChanged = onHandleChanged
            )
            SimpleInputField(
                text = "Change Password:",
                valueStateFlow = passwordStateFlow,
                onValueChanged = onPasswordChanged,
                keyboardType = KeyboardType.Password,
                passwordType = true
            )
        }
    }

}
