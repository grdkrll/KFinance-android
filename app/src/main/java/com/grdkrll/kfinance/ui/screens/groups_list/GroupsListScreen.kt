package com.grdkrll.kfinance.ui.screens.groups_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.model.dto.groups.response.GroupResponse
import com.grdkrll.kfinance.sealed.GroupsState
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.grdkrll.kfinance.ui.components.BottomNavigationBar
import com.grdkrll.kfinance.ui.components.SimpleCircularProgressIndicator
import com.grdkrll.kfinance.ui.components.SimpleFloatingActionButton
import com.grdkrll.kfinance.ui.components.TopBar

/**
 * A Composable Function used to display a screen with a List of all the Groups of the User
 */
@Composable
fun GroupsListScreen(
    viewModel: GroupsListViewModel = koinViewModel()
) {
    val userGroup = viewModel.getUser() ?: throw Exception()
    val (user, group) = userGroup
    viewModel.fetchGroups()
    Scaffold(
        topBar = { TopBar(user = user, group = group, viewModel::onDeselectGroupClicked) },
        bottomBar = {
            BottomNavigationBar(
                listOf(false, true, false),
                listOf(viewModel::redirectToHome, {}, viewModel::redirectToProfile)
            )
        },
        floatingActionButton = { SimpleFloatingActionButton(onClicked = viewModel::onCreateGroupButtonClicked) },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues)
        ) {
            GroupsListList(
                response = viewModel.response,
                viewModel::onSelectGroupClicked,
                user.id,
                viewModel::redirectToGroupSettings
            )
        }
    }
}

/**
 * A Composable Function used to display a List of all the Groups of the User
 */
@Composable
fun GroupsListList(
    response: MutableState<GroupsState>,
    onSelectGroupClicked: (Int, String) -> Unit,
    userId: Int,
    redirectToGroupSettings: (String, String) -> Unit
) {
    when (val result = response.value) {
        is GroupsState.Success -> {
            LazyColumn {
                items(result.data) { group ->
                    GroupCard(group, onSelectGroupClicked, userId, redirectToGroupSettings)
                }
            }
        }

        is GroupsState.Loading -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                SimpleCircularProgressIndicator()
            }
        }

        else -> {
            Text("Something went wrong")
        }
    }
}

/**
 * A Composable Function used to display a Card with data of a single Group
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GroupCard(
    group: GroupResponse,
    onGroupClicked: (Int, String) -> Unit,
    userId: Int,
    redirectToGroupSettings: (String, String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .clickable { onGroupClicked(group.id, group.name) }
    ) {
        Column {
            Row {
                Column {
                    Text(group.name, style = TextStyle(fontSize = 24.sp))
                    Text("@${group.handle}", style = TextStyle(fontSize = 14.sp))
                }
                if (userId == group.ownerId) {
                    Spacer(modifier = Modifier.weight(1.0f))
                    IconButton(onClick = {
                        redirectToGroupSettings(
                            group.name,
                            group.handle
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            "Group's Settings"
                        )
                    }
                }
            }
            Divider(thickness = 1.dp, color = Color.Black)
        }
    }
}