package com.grdkrll.kfinance.ui.screens.home

import android.icu.text.DateFormat.getDateInstance
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.model.dto.transaction.response.TransactionResponse
import com.grdkrll.kfinance.sealed.TransactionState
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import com.grdkrll.kfinance.repository.SortType
import kotlinx.coroutines.flow.StateFlow
import java.util.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val user = viewModel.getUser()
    if (user == null) {
        viewModel.redirectToPreLogin()
        return
    }
    viewModel.fetchTransactions()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                {},
                viewModel::redirectToGroupsList,
                viewModel::redirectToProfile
            )
        },
        floatingActionButton = { AddTransactionButton(viewModel::redirectToAddTransaction) },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(paddingValues)
            ) {
                Text(user.email)
                Text(user.handle)
                SortField(viewModel.sortType, viewModel::onSortTypeChanged)
                TransactionList(viewModel.response)
            }
        }
    )
}

@Composable
fun AddTransactionButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .padding(8.dp)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add transaction")
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
            selected = true,
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
            selected = false,
            onClick = redirectToProfile
        )
    }
}

@Composable
fun TransactionList(
    response: MutableState<TransactionState>
) {
    when (val result = response.value) {
        is TransactionState.Success -> {
            LazyColumn {
                items(result.data.result) { transaction ->
                    TransactionCard(transaction)
                }
            }
        }
        is TransactionState.Loading -> {
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
fun TransactionCard(transaction: TransactionResponse) {
    val formatter = getDateInstance()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .padding(8.dp)
    ) {
        Column {
            Text(text = AnnotatedString(transaction.sum.toString()))
            Text(text = AnnotatedString(transaction.category.name))
            Text(text = AnnotatedString(formatter.format(Date(transaction.timestamp))))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortField(
    sortTypeState: StateFlow<SortField>,
    onSortTypeChanged: (String) -> Unit
) {
    val sortType = sortTypeState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = sortType.value.sortType.name,
            onValueChange = { },
            label = { Text("SortType") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            SortType.values().forEach { sortType ->
                DropdownMenuItem(
                    text = { Text(sortType.name) },
                    onClick = {
                        onSortTypeChanged(sortType.name)
                        expanded = false
                    }
                )
            }
        }
    }
}