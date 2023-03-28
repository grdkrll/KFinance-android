package com.grdkrll.kfinance.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.ui.screens.add_transaction.AddTransactionScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val user = viewModel.getUser()
    if (user == null) {
        viewModel.redirectToPreLogin()
        return
    }
    Scaffold(
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
//        viewModel.getTransactions().result.forEach {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(32.dp)
//                    .padding(8.dp)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                ) {
//                    Text(text = AnnotatedString(it.sum.toString()))
//                    Text(text = AnnotatedString(it.category.name))
//                    Text(text = AnnotatedString(it.timestamp.toString()))
//                }
//            }
//        }
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
        Text("+")
    }
}