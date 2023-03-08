import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.grdkrll.kfinance.R
import com.grdkrll.kfinance.ui.components.input_fields.InputField
import kotlinx.coroutines.flow.StateFlow

@Composable
fun EmailInputField(
    emailField: StateFlow<InputField>,
    onEmailChanged: (String) -> Unit
) {
    val email = emailField.collectAsState()
    OutlinedTextField(
        label = { Text("Email") },
        value = email.value.inputField,
        onValueChange = onEmailChanged,
        leadingIcon = { Icon(
            imageVector = Icons.Filled.Email,
            contentDescription = stringResource(id = R.string.email_icon)
        ) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )
}