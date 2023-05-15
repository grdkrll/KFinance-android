package com.grdkrll.kfinance.ui.screens.home

import android.icu.text.DateFormat.getDateInstance
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.model.dto.transaction.response.TransactionResponse
import com.grdkrll.kfinance.sealed.TransactionState
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.grdkrll.kfinance.ui.components.BottomNavigationBar
import com.grdkrll.kfinance.ui.components.CategoryPicker
import com.grdkrll.kfinance.ui.components.SimpleCircularProgressIndicator
import com.grdkrll.kfinance.ui.components.SimpleFloatingActionButton
import com.grdkrll.kfinance.ui.components.sumColorPicker
import com.grdkrll.kfinance.ui.components.TopBar
import kotlinx.coroutines.flow.StateFlow
import java.util.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val userGroup = viewModel.getUser()
    if (userGroup == null) {
        viewModel.redirectToPreLogin()
        return
    }
    val (user, group) = userGroup
    viewModel.fetchTransactions()
    viewModel.fetchTotal()
    Scaffold(
        topBar = { TopBar(user, group, viewModel::onDeselectGroupButtonClicked) },
        bottomBar = {
            BottomNavigationBar(
                listOf(true, false, false),
                listOf({}, viewModel::redirectToGroupsList, viewModel::redirectToProfile)
            )
        },
        floatingActionButton = { SimpleFloatingActionButton(onClicked = viewModel::onAddTransactionButtonClicked) },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues)
        ) {
            PeriodPicker(
                numberOfPeriods = viewModel.numberOfPeriods,
                periodsList = viewModel.periodsList,
                onPeriodClicked = viewModel::onPeriodClicked,
                selectedPeriodStateFlow = viewModel.selectedPeriod
            )
            Spacer(modifier = Modifier.height(30.dp))
            TotalSumCard(
                selectedPeriodStateFlow = viewModel.selectedPeriod,
                periodsList = viewModel.periodsList,
                periodSumStateFlow = viewModel.periodSum,
                totalState = viewModel.responseTotal
            )
            CategoryPicker(viewModel.category, viewModel::onCategoryChanged)
            TransactionList(viewModel.response, viewModel::redirectToAllTransactions)
        }
    }
}

@Composable
fun PeriodPicker(
    numberOfPeriods: Int,
    periodsList: List<String>,
    onPeriodClicked: (Int) -> Unit,
    selectedPeriodStateFlow: StateFlow<TimePeriodField>
) {
    val selectedPeriod = selectedPeriodStateFlow.collectAsState()
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(numberOfPeriods) { i ->
            ClickableText(
                text = AnnotatedString(periodsList[i]),
                style = TextStyle(
                    color = if (selectedPeriod.value.period == i) Color.Black else Color.Gray,
                    fontSize = 20.sp
                ),
                onClick = { onPeriodClicked(i) },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun TotalSumCard(
    selectedPeriodStateFlow: StateFlow<TimePeriodField>,
    periodsList: List<String>,
    periodSumStateFlow: StateFlow<IntField>,
    totalState: MutableState<Boolean>
) {
    val date = getDateInstance().format(Date())
    val selectedPeriod = selectedPeriodStateFlow.collectAsState()
    Card(
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            Color(197, 183, 134)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        val periodSum = periodSumStateFlow.collectAsState()
        Column {
            Text(
                date,
                style = TextStyle(fontSize = 24.sp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Text(
                periodsList[selectedPeriod.value.period] + "'s total:",
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row {
                Spacer(
                    modifier = Modifier
                        .weight(1.0f)
                )
                when (totalState.value) {
                    false -> SimpleCircularProgressIndicator()
                    true -> {
                        Text(
                            "${periodSum.value.value}$",
                            modifier = Modifier.padding(16.dp),
                            style = TextStyle(
                                color = sumColorPicker(periodSum.value.value),
                                fontSize = 24.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionList(
    response: MutableState<TransactionState>,
    redirectToAllTransactions: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {
        Row {
            Text("Recent:", style = TextStyle(fontSize = 16.sp))
            Spacer(modifier = Modifier.weight(1.0f))
            ClickableText(
                text = AnnotatedString("Show all"),
                style = TextStyle(fontSize = 16.sp),
                onClick = { redirectToAllTransactions() },
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        Card(
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(
                Color(197, 183, 134)
            )
        ) {
            when (val result = response.value) {
                is TransactionState.Success -> {
                    Column {
                        LazyColumn {
                            items(result.data.result.reversed()) { transaction ->
                                TransactionCard(transaction)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                is TransactionState.Loading -> {
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
    }
}

@Composable
fun TransactionCard(transaction: TransactionResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            Text(transaction.message, style = TextStyle(fontSize = 18.sp))
            Row {
                Column {
                    Column {
                        Text(transaction.category.name, style = TextStyle(fontSize = 14.sp))
                        Text(
                            transaction.timestamp,
                            style = TextStyle(fontSize = 12.sp)
                        )
                    }
                    Text(transaction.ownerHandle, style = TextStyle(fontSize = 14.sp))
                }
                Spacer(modifier = Modifier.weight(1.0f))
                Text(
                    "${transaction.sum}$",
                    style = TextStyle(color = sumColorPicker(transaction.sum), fontSize = 24.sp)
                )
            }
            Divider(color = Color.White, thickness = 1.dp)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}