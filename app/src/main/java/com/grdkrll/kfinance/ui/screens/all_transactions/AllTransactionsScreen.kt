package com.grdkrll.kfinance.ui.screens.all_transactions

import android.icu.text.DateFormat
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grdkrll.kfinance.model.dto.transaction.response.TransactionResponse
import com.grdkrll.kfinance.sealed.TransactionState
import com.grdkrll.kfinance.ui.components.CategoryPicker
import com.grdkrll.kfinance.ui.components.SimpleCircularProgressIndicator
import com.grdkrll.kfinance.ui.components.buttons.CloseButton
import com.grdkrll.kfinance.ui.components.sumColorPicker
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@Composable
fun AllTransactionsScreen(
    viewModel: AllTransactionsViewModel = koinViewModel()
) {
    viewModel.fetchTransactions()
    Scaffold(
        topBar = {
            Row {
                Spacer(modifier = Modifier.weight(1.0f))
                CloseButton(viewModel::onCloseButtonClicked)
            }
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues)
        ) {
            Pager(
                pagerStateFlow = viewModel.page,
                incrementPage = viewModel::incrementPage,
                decrementPage = viewModel::decrementPage
            )
            CategoryPicker(
                categoryStateFlow = viewModel.category,
                onCategoryClicked = viewModel::onCategoryChanged
            )
            TransactionList(viewModel.response)
        }
    }
}

@Composable
fun Pager(
    pagerStateFlow: StateFlow<PagerField>,
    incrementPage: () -> Unit,
    decrementPage: () -> Unit
) {
    val pager = pagerStateFlow.collectAsState()
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = decrementPage) {
            Icon(imageVector = Icons.Filled.ArrowLeft, contentDescription = null)
        }
        Text(pager.value.page.toString())
        IconButton(onClick = incrementPage) {
            Icon(imageVector = Icons.Filled.ArrowRight, contentDescription = null)
        }
    }
}

@Composable
fun TransactionList(
    response: MutableState<TransactionState>
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {
        Card(
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(
                Color(197, 183, 134)
            )
        ) {
            when (val result = response.value) {
                is TransactionState.Success -> {
                    LazyColumn {
                        val transactionsCards = mutableListOf<MutableList<TransactionResponse>>()
                        for (transaction in result.data.result) {
                            if (transactionsCards.isNotEmpty() && (Instant.parse(transactionsCards[transactionsCards.lastIndex][transactionsCards[transactionsCards.lastIndex].lastIndex].timestamp)
                                    .atZone(
                                        ZoneId.systemDefault()
                                    ).toLocalDate()).dayOfMonth ==
                                (Instant.parse(transaction.timestamp)
                                    .atZone(ZoneId.systemDefault())).dayOfMonth
                            ) {
                                transactionsCards[transactionsCards.lastIndex].add(transaction)
                            } else {
                                transactionsCards.add(mutableListOf(transaction))
                            }
                        }
                        items(transactionsCards.reversed()) { transactions ->
                            TransactionCard(transactions)
                        }
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
fun TransactionCard(transactions: MutableList<TransactionResponse>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            transactions.reversed().forEach { transaction ->
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
                            style = TextStyle(
                                color = sumColorPicker(transaction.sum),
                                fontSize = 24.sp
                            )
                        )
                    }
                    Divider(color = Color.White, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}