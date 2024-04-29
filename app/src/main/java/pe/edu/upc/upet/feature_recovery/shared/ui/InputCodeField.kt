package pe.edu.upc.upet.feature_recovery.shared.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun InputDigit(input: MutableState<String>){
    OutlinedTextField(
        modifier = Modifier
            .width(70.dp)
            .padding(2.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp)), // Rounded corners
        value = input.value,
        onValueChange = { newValue ->
            if (newValue.length <= 1) {
                input.value = newValue.uppercase() // Enforce uppercase and limit to 1 char
            }
        },
        textStyle = TextStyle(color = Color.Black), // Set text color to black
        singleLine = true // Limit to single line
    )

}