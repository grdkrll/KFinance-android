package com.grdkrll.kfinance.ui.screens.add_transaction

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.R
import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.ui.components.input_fields.SumInputField
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel = koinViewModel()
) {
    TransactionBox(viewModel)
}

@Composable
fun TransactionBox(
    viewModel: AddTransactionViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CloseButton(viewModel::onCloseButtonClicked)
        CategoryField(
            viewModel::onCategoryChanged
        )

        SumField(
            viewModel.sum,
            viewModel::onSumChanged
        )

        AddTransactionButton(viewModel::onAddTransactionButtonClicked)

    }
}

@Composable
fun CategoryField(
    onCategoryChanged: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(true) }
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        TransactionCategory.values().forEachIndexed { index, transactionCategory ->
            DropdownMenuItem(
                text = { Text(transactionCategory.name) },
                onClick = {
                    onCategoryChanged(transactionCategory.name)
                    expanded.value = false
                }
            )
        }
    }
}

@Composable
fun SumField(
    sumInputField: StateFlow<SumInputField>,
    onSumChange: (String) -> Unit
) {
    val sum = sumInputField.collectAsState()
    OutlinedTextField(
        label = { Text("Total sum") },
        value = sum.value.inputField.toString(),
        onValueChange = onSumChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        )
    )
}

@Composable
fun CloseButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close_icon)
        )
    }
}

@Composable
fun AddTransactionButton(
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.extraSmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(8.dp)
    ) {
        Text("Add transaction")
    }
}