package com.grdkrll.kfinance.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.R
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.StateFlow

/**
 * A Composable Functions used to display Input Fields
 *
 * @param text the text that will be shown above the Input Field
 * @param valueStateFlow a [StateFlow] instance used to display current text in the Input Field
 * @param onValueChanged a Function called whenever text inside the Input Field changes (as an argument gets a new string inside the Input Field)
 * @param keyboardType an instance of [KeyboardType] that will be used for the Input Field (default is set to [KeyboardType.Text])
 * @param passwordType a [Boolean] value that indicates whether Input Field will work as a Password Input Field or not (default is set to false)
 *
 * If [passwordType] is set to true A Icon Button Toggle will appear to switch between showing the password and not (by default the password will not show)
 */
@Composable
fun SimpleInputField(
    text: String,
    valueStateFlow: StateFlow<InputField>,
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    passwordType: Boolean = false
) {
    val value = valueStateFlow.collectAsState()
    val passwordVisible = remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Text(text, modifier = Modifier.padding(8.dp))
        TextField(
            value = value.value.inputField,
            onValueChange = onValueChanged,
            trailingIcon = {
                if (passwordType) {
                    IconButton(onClick = {
                        passwordVisible.value = !passwordVisible.value
                    }) {
                        if (passwordVisible.value) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = stringResource(id = R.string.visibility_on_icon)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = stringResource(id = R.string.visibility_off_icon)
                            )
                        }
                    }
                }
            },
            supportingText = {
                if (value.value.isError) {
                    Text(value.value.errorMessage)
                }
            },
            isError = value.value.isError,
            visualTransformation = if (!passwordType || passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    }
}

/**
 * A Simple Input Field with a Card around it
 *
 * @param text the text that will be shown above the Input Field
 * @param valueStateFlow a [StateFlow] instance used to display current text in the Input Field
 * @param onValueChanged a Function called whenever text inside the Input Field changes (as an argument gets a new string inside the Input Field)
 * @param keyboardType an instance of [KeyboardType] that will be used for the Input Field (default is set to [KeyboardType.Text])
 * @param passwordType a [Boolean] value that indicates whether Input Field will work as a Password Input Field or not (default is set to false)
 *
 * If [passwordType] is set to true A Icon Button Toggle will appear to switch between showing the password and not (by default the password will not show)
 */
@Composable
fun SimpleInputFieldWithCard(
    text: String,
    valueStateFlow: StateFlow<InputField>,
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    passwordType: Boolean = false
) {
    Card(
        shape = MaterialTheme.shapes.small,
        backgroundColor = Color(197, 183, 134),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        SimpleInputField(
            text = text,
            valueStateFlow = valueStateFlow,
            onValueChanged = onValueChanged,
            keyboardType = keyboardType,
            passwordType = passwordType
        )
    }
}
