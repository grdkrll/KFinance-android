package com.grdkrll.kfinance.ui.screens.profile

import EmailInputField
import PasswordInputField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.ui.components.input_fields.HandleInputField
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = koinViewModel()
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                viewModel::onRedirectToHomeClicked,
                viewModel::onRedirectToGroupsListClicked,
                {})
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues)
        ) {
            ProfileBox(
                viewModel.handle,
                viewModel::onHandleChange,
                viewModel.email,
                viewModel::onEmailChange,
                viewModel.password,
                viewModel::onPasswordChange,
                viewModel.confirmPassword,
                viewModel::onConfirmPasswordChange,
                viewModel::onConfirmButtonClicked
            )
        }
    }
}

@Composable
fun ProfileBox(
    handleState: StateFlow<InputField>,
    onHandleChanged: (String) -> Unit,
    emailState: StateFlow<InputField>,
    onEmailChanged: (String) -> Unit,
    passwordState: StateFlow<InputField>,
    onPasswordChange: (String) -> Unit,
    confirmPasswordState: StateFlow<InputField>,
    onConfirmPasswordChange: (String) -> Unit,
    onConfirmButtonClicked: () -> Unit

) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        HandleInputField(handleField = handleState, onHandleChanged = onHandleChanged)
        EmailInputField(emailField = emailState, onEmailChanged = onEmailChanged)
        PasswordInputField(passwordField = passwordState, onPasswordChange = onPasswordChange)
        PasswordInputField(
            passwordField = confirmPasswordState,
            onPasswordChange = onConfirmPasswordChange,
            passwordLabel = "Confirm password"
        )
        ConfirmButton(onConfirmButtonClicked)
    }
}

@Composable
fun BottomNavigationBar(
    redirectToHome: () -> Unit,
    redirectToGroups: () -> Unit,
    redirectToProfile: () -> Unit
) {
    BottomNavigation(
        contentColor = MaterialTheme.colorScheme.primary,
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Filled.Home, "Home") },
            label = { Text("Home") },
            alwaysShowLabel = false,
            selected = false,
            onClick = redirectToHome
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Filled.People, "Groups") },
            label = { Text("Groups") },
            alwaysShowLabel = false,
            selected = false,
            onClick = redirectToGroups
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Filled.Person, "Profile") },
            label = { Text("Profile") },
            alwaysShowLabel = false,
            selected = true,
            onClick = redirectToProfile
        )
    }
}

@Composable
fun ConfirmButton(
    onConfirmButtonClicked: () -> Unit
) {
    OutlinedButton(
        shape = MaterialTheme.shapes.extraSmall, onClick = onConfirmButtonClicked,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(8.dp)
    ) {
        Text("Confirm")
    }
}