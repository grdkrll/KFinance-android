package com.grdkrll.kfinance.ui.components.input_fields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HandleInputField(
    handleField: StateFlow<InputField>,
    onHandleChanged: (String) -> Unit
) {
    val handle = handleField.collectAsState()
    OutlinedTextField(
        label = { Text("Profile") },
        value = handle.value.inputField,
        onValueChange = onHandleChanged,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile"
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    )
}