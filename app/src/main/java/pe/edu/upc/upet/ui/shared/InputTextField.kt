package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InputTextField(input: MutableState<String>, placeholder: String) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp)), // Rounded corners
        placeholder = {
            Text(text = placeholder, color = Color(0xFFB3B3B3))
        },
        value = input.value,
        onValueChange = {
            input.value = it
        }
    )
}