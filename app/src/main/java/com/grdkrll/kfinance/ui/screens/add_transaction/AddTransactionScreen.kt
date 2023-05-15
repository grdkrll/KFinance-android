package com.grdkrll.kfinance.ui.screens.add_transaction

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.TransactionCategory
import com.grdkrll.kfinance.ui.components.buttons.CloseButton
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material.Card
import com.grdkrll.kfinance.ui.components.CategoryPicker
import com.grdkrll.kfinance.ui.components.SimpleInputField
import com.grdkrll.kfinance.ui.components.SimpleInputFieldWithCard
import com.grdkrll.kfinance.ui.components.buttons.SimpleButton

@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel = koinViewModel()
) {
    Scaffold(
        topBar = {
            Row {
                Spacer(modifier = Modifier.weight(1.0f))
                CloseButton(viewModel::onCloseButtonClicked)
            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TransactionBox(
                viewModel.message,
                viewModel::onMessageChanged,
                viewModel.sum,
                viewModel::onSumChanged
            )
            CategoryPicker(
                categoryStateFlow = viewModel.category,
                onCategoryClicked = viewModel::onCategoryChanged
            )
            SimpleButton(
                text = "Add Transaction",
                onClicked = viewModel::onAddTransactionButtonClicked
            )
        }
    }
}

@Composable
fun TransactionBox(
    messageStateFlow: StateFlow<InputField>,
    onMessageChanged: (String) -> Unit,
    sumStateFlow: StateFlow<InputField>,
    onSumChanged: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleInputFieldWithCard(
            text = "Message:",
            valueStateFlow = messageStateFlow,
            onValueChanged = onMessageChanged
        )
        SimpleInputFieldWithCard(
            text = "Total:",
            valueStateFlow = sumStateFlow,
            onValueChanged = onSumChanged,
            keyboardType = KeyboardType.Number
        )
    }
}
