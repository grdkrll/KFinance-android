package com.grdkrll.kfinance.ui.components.input_fields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MyInputField(
    text: String,
    inputField: StateFlow<InputField>,
    onValueChanged: (String) -> Unit,
    icon: ImageVector? = null,
    contentDescription: String = text
) {
    val value = inputField.collectAsState()
    OutlinedTextField(
        label = { Text(text) },
        value = value.value.inputField,
        onValueChange = onValueChanged,
        leadingIcon = {
            if(icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Ascii,
            imeAction = ImeAction.Next
        )
    )
}