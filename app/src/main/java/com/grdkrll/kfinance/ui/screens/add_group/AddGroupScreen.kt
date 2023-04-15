package com.grdkrll.kfinance.ui.screens.add_group

import PasswordInputField
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.ui.components.buttons.CloseButton
import com.grdkrll.kfinance.ui.components.buttons.MyOutlinedButton
import com.grdkrll.kfinance.ui.components.input_fields.HandleInputField
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddGroupScreen(
    viewModel: AddGroupViewModel = koinViewModel()
) {
    Scaffold(
        topBar = { CloseButton(viewModel::onCloseButtonClicked) },
        content = { paddingValues ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AddGroupBox(
                    handleFieldState = viewModel.handle,
                    onHandleChanged = viewModel::onHandleChanged,
                    passwordFieldState = viewModel.password,
                    onPasswordChanged = viewModel::onPasswordChanged
                )
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                MyOutlinedButton("Add group", viewModel::onAddGroupButtonClicked)
                Text("Or", modifier = Modifier.padding(vertical = 4.dp))
                MyOutlinedButton("Create group", viewModel::onCreateGroupButtonClicked)
            }
        }
    )
}

@Composable
fun AddGroupBox(
    handleFieldState: StateFlow<InputField>,
    onHandleChanged: (String) -> Unit,
    passwordFieldState: StateFlow<InputField>,
    onPasswordChanged: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HandleInputField(handleFieldState, onHandleChanged)
        PasswordInputField(passwordFieldState, onPasswordChanged)
    }
}