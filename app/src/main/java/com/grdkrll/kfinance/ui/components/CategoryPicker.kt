package com.grdkrll.kfinance.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import com.grdkrll.kfinance.TransactionCategory
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.ui.screens.add_transaction.CategoryInputField
import kotlinx.coroutines.flow.StateFlow

/**
 * A Composable Function used to display a picker of Categories
 *
 * @param categoryStateFlow a [StateFlow] instance used to display currently selected category
 * @param onCategoryClicked a Function used whenever a category is clicked (as an arguments gets the name of the clicked category)
 */
@Composable
fun CategoryPicker(
    categoryStateFlow: StateFlow<CategoryInputField>,
    onCategoryClicked: (String) -> Unit
) {
    val category = categoryStateFlow.collectAsState()
    LazyRow(
        userScrollEnabled = true,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp)
    ) {
        items(TransactionCategory.values().sorted()) {
            Card(
                border = BorderStroke(
                    1.dp,
                    if (it.name == category.value.category.name) Color.Black else Color.Gray
                ),
                shape = MaterialTheme.shapes.extraSmall,
                colors = CardDefaults.cardColors(Color.White),
                modifier = Modifier
                    .clickable { onCategoryClicked(it.name) }
                    .padding(4.dp)
            ) {
                Column {
                    Text(
                        it.name,
                        color = if (it.name == category.value.category.name) Color.Black else Color.Gray,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}