package com.grdkrll.kfinance.ui.screens.create_group

import PasswordInputField
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.ui.components.buttons.CloseButton
import com.grdkrll.kfinance.ui.components.buttons.MyOutlinedButton
import com.grdkrll.kfinance.ui.components.input_fields.HandleInputField
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import com.grdkrll.kfinance.ui.components.input_fields.MyInputField
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel


@Composable
fun CreateGroupScreen(
    viewModel: CreateGroupViewModel = koinViewModel()
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
                CreateGroupBox(
                    nameFieldState = viewModel.name,
                    onNameChanged = viewModel::onNameChanged,
                    handleFieldState = viewModel.handle,
                    onHandleChanged = viewModel::onHandleChanged,
                    passwordFieldState = viewModel.password,
                    onPasswordChanged = viewModel::onPasswordChanged
                )
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                MyOutlinedButton("Add group", viewModel::onCreateGroupButtonClicked)
                Text("Or", modifier = Modifier.padding(vertical = 4.dp))
                MyOutlinedButton("Create group", viewModel::onAddGroupButtonClicked)
            }
        }
    )
}

@Composable
fun CreateGroupBox(
    nameFieldState: StateFlow<InputField>,
    onNameChanged: (String) -> Unit,
    handleFieldState: StateFlow<InputField>,
    onHandleChanged: (String) -> Unit,
    passwordFieldState: StateFlow<InputField>,
    onPasswordChanged: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyInputField("Group name", nameFieldState, onNameChanged, Icons.Filled.People)
        HandleInputField(handleFieldState, onHandleChanged)
        PasswordInputField(passwordFieldState, onPasswordChanged)
    }
}
