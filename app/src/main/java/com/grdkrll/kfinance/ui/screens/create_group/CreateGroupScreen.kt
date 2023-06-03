package com.grdkrll.kfinance.ui.screens.create_group

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.ui.components.SimpleCircularProgressIndicator
import com.grdkrll.kfinance.ui.components.SimpleInputField
import com.grdkrll.kfinance.ui.components.buttons.CloseButton
import com.grdkrll.kfinance.ui.components.buttons.SimpleButton
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

/**
 * A Composable Function used to display a screen to Create a new Group
 */
@Composable
fun CreateGroupScreen(
    viewModel: CreateGroupViewModel = koinViewModel()
) {
    Scaffold(
        topBar = {
            Row {
                Spacer(modifier = Modifier.weight(1.0f))
                CloseButton(viewModel::onCloseButtonClicked)
            }
        },
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (viewModel.loading.value) {

                false -> {
                    CreateGroupBox(
                        nameStateFlow = viewModel.name,
                        onNameChanged = viewModel::onNameChanged,
                        handleStateFlow = viewModel.handle,
                        onHandleChanged = viewModel::onHandleChanged,
                        passwordStateFlow = viewModel.password,
                        onPasswordChanged = viewModel::onPasswordChanged
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SimpleButton(
                        text = "Create Group",
                        onClicked = viewModel::onCreateGroupButtonClicked
                    )
                    Spacer(modifier = Modifier.weight(1.0f))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Already have a group?")
                        ClickableText(
                            text = AnnotatedString("Join in"),
                            style = TextStyle(color = Color(217, 203, 79)),
                            onClick = { viewModel.onJoinGroupButtonClicked() },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }

                true -> SimpleCircularProgressIndicator()
            }
        }
    }
}

/**
 * A Composable Function used to Display Input Fields that hold basic information about the Group
 */
@Composable
fun CreateGroupBox(
    nameStateFlow: StateFlow<InputField>,
    onNameChanged: (String) -> Unit,
    handleStateFlow: StateFlow<InputField>,
    onHandleChanged: (String) -> Unit,
    passwordStateFlow: StateFlow<InputField>,
    onPasswordChanged: (String) -> Unit
) {
    androidx.compose.material3.Card(
        colors = CardDefaults.cardColors(
            Color(197, 183, 134)
        ),
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleInputField(
                text = "Group's Name:",
                valueStateFlow = nameStateFlow,
                onValueChanged = onNameChanged
            )
            SimpleInputField(
                text = "Group's Handle:",
                valueStateFlow = handleStateFlow,
                onValueChanged = onHandleChanged
            )
            SimpleInputField(
                text = "Group's Password",
                valueStateFlow = passwordStateFlow,
                onValueChanged = onPasswordChanged,
                keyboardType = KeyboardType.Password,
                passwordType = true
            )
        }
    }
}
