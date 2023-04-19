package com.grdkrll.kfinance.ui.screens.groups_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
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

@Composable
fun GroupsList(
    viewModel: GroupsListViewModel = koinViewModel()
) {
    viewModel.fetchGroups()
    Scaffold(
        floatingActionButton = { AddGroupButton(viewModel::onAddGroupButtonClicked) },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            BottomNavigationBar(
                redirectToHome = viewModel::redirectToHome,
                redirectToGroups = {},
                viewModel::redirectToProfile
            )
        },
        content = { paddingValues ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(paddingValues)
            ) {
                Text("Groups")
                GroupsListList(response = viewModel.response, viewModel::onSelectGroupClicked)
            }
        }
    )
}

@Composable
fun BottomNavigationBar(
    redirectToHome: () -> Unit,
    redirectToGroups: () -> Unit,
    redirectToProfile: () -> Unit,
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
            selected = true,
            onClick = redirectToGroups
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Filled.Person, "Profile") },
            label = { Text("Profile") },
            alwaysShowLabel = false,
            selected = false,
            onClick = redirectToProfile
        )
    }
}

@Composable
fun AddGroupButton(
    addGroup: () -> Unit
) {
    FloatingActionButton(
        onClick = addGroup,
        modifier = Modifier
            .padding(16.dp)
            .padding(8.dp)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Group")
    }
}

@Composable
fun GroupsListList(
    response: MutableState<GroupsState>,
    onSelectGroupClicked: (Int, String) -> Unit
) {
    when (val result = response.value) {
        is GroupsState.Success -> {
            LazyColumn {
                items(result.data) { group ->
                    GroupCard(group, onSelectGroupClicked)
                }
            }
        }

        is GroupsState.Loading -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {
            Text("Something went wrong")
        }
    }
}

@Composable
fun GroupCard(group: GroupResponse, onGroupClicked: (Int, String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .padding(8.dp)
            .clickable { onGroupClicked(group.id, group.name) }
    ) {
        Column {
            Text(group.name)
            Text(group.handle)
        }
    }
}