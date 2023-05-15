package com.grdkrll.kfinance.ui.screens.group_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.model.dto.groups.response.MemberResponse
import com.grdkrll.kfinance.sealed.MembersState
import com.grdkrll.kfinance.ui.components.SimpleInputField
import com.grdkrll.kfinance.ui.components.SimpleInputFieldWithCard
import com.grdkrll.kfinance.ui.components.buttons.CloseButton
import com.grdkrll.kfinance.ui.components.buttons.SimpleButton
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.grdkrll.kfinance.ui.components.SimpleCircularProgressIndicator

@Composable
fun GroupSettingsScreen(
    viewModel: GroupSettingsViewModel = koinViewModel()
) {
    Scaffold(
        topBar = {
            Row {
                Spacer(modifier = Modifier.weight(1.0f))
                CloseButton(viewModel::onCloseButtonClicked)
            }
        },

        ) { paddingValues ->
        viewModel.fetchMembers()
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState(), true)
        ) {
            ChangeGroupBox(
                nameStateFlow = viewModel.name,
                onNameChanged = viewModel::onNameChanged,
                handleStateFlow = viewModel.handle,
                onHandleChanged = viewModel::onHandleChanged,
                passwordStateFlow = viewModel.password,
                onPasswordChanged = viewModel::onPasswordChanged
            )
            SimpleInputFieldWithCard(
                text = "Confirm Password",
                valueStateFlow = viewModel.confirmPassword,
                onValueChanged = viewModel::onConfirmPasswordChanged,
                keyboardType = KeyboardType.Password,
                passwordType = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            SimpleButton(text = "Change Group", onClicked = viewModel::onChangeGroupButtonClicked)
            MembersList(viewModel.response, viewModel::removeMember)
        }
    }
}


@Composable
fun ChangeGroupBox(
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

@Composable
fun MembersList(
    response: MutableState<MembersState>,
    onRemoveMemberClicked: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(24.dp)
    ) {
        when (val result = response.value) {
            is MembersState.Success -> {
                LazyColumn {
                    items(result.data) { member ->
                        MemberCard(member, onRemoveMemberClicked)
                    }
                }
            }

            is MembersState.Loading -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SimpleCircularProgressIndicator()
                }
            }

            else -> {
                Text("Something went wrong")
            }
        }
    }
}

@Composable
fun MemberCard(member: MemberResponse, onRemoveMemberClicked: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Column {
            Row {
                Column {
                    Text(member.name, style = TextStyle(fontSize = 24.sp))
                    Text("@${member.handle}", style = TextStyle(fontSize = 14.sp))
                }
                Spacer(modifier = Modifier.weight(1.0f))
                IconButton(onClick = {
                    onRemoveMemberClicked(
                        member.handle
                    )
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        "Remove member"
                    )
                }
            }
            Divider(thickness = 1.dp, color = Color.Black)
        }
    }
}