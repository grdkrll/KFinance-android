package com.grdkrll.kfinance.ui.screens.add_transaction

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.ui.components.buttons.CloseButton
import com.grdkrll.kfinance.ui.components.input_fields.InputField
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
    Scaffold(
        topBar = { CloseButton(viewModel::onCloseButtonClicked) },
        content = { paddingValues ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                CategoryField(
                    viewModel.category,
                    viewModel::onCategoryChanged
                )

                SumField(
                    viewModel.sum,
                    viewModel::onSumChanged
                )

                AddTransactionButton(viewModel::onAddTransactionButtonClicked)

            }
        })
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CategoryField(
    categoryState: StateFlow<CategoryInputField>,
    onCategoryChanged: (String) -> Unit
) {
    val category = categoryState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = category.value.category.name,
            onValueChange = { },
            label = { Text("Category") },
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
            TransactionCategory.values().forEach { transactionCategory ->
                DropdownMenuItem(
                    text = { Text(transactionCategory.name) },
                    onClick = {
                        onCategoryChanged(transactionCategory.name)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SumField(
    sumInputField: StateFlow<InputField>,
    onSumChange: (String) -> Unit
) {
    val sum = sumInputField.collectAsState()
    OutlinedTextField(
        label = { Text("Total sum") },
        value = sum.value.inputField,
        onValueChange = onSumChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
    )
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