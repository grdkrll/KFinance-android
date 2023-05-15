package com.grdkrll.kfinance.ui.screens.join_group

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.ui.components.SimpleCircularProgressIndicator
import com.grdkrll.kfinance.ui.components.SimpleInputField
import com.grdkrll.kfinance.ui.components.buttons.CloseButton
import com.grdkrll.kfinance.ui.components.buttons.SimpleButton
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun JoinGroupScreen(
    viewModel: JoinGroupViewModel = koinViewModel()
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
                    AddGroupBox(
                        handleStateFlow = viewModel.handle,
                        onHandleChanged = viewModel::onHandleChanged,
                        passwordStateFlow = viewModel.password,
                        onPasswordChanged = viewModel::onPasswordChanged
                    )
                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                    SimpleButton(
                        text = "Join Group",
                        onClicked = viewModel::onJoinGroupButtonClicked
                    )
                    Spacer(modifier = Modifier.weight(1.0f))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Want to create a group?")
                        ClickableText(
                            text = AnnotatedString("Create"),
                            style = TextStyle(color = Color(217, 203, 79)),
                            onClick = { viewModel.onCreateGroupButtonClicked() },
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
fun AddGroupBox(
    handleStateFlow: StateFlow<InputField>,
    onHandleChanged: (String) -> Unit,
    passwordStateFlow: StateFlow<InputField>,
    onPasswordChanged: (String) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            Color(197, 183, 134)
        )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleInputField(
                text = "Group's Handle",
                valueStateFlow = handleStateFlow,
                onValueChanged = onHandleChanged
            )
            SimpleInputField(
                text = "Group's Password",
                valueStateFlow = passwordStateFlow,
                onValueChanged = onPasswordChanged
            )
        }
    }
}
