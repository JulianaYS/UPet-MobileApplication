package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.ui.theme.Gray1
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun InputTextField( input: MutableState<String>, placeholder: String, label: String){
    val commonPadding = 8.dp
    val cornerSize = 10.dp


    Text(text=label, modifier = Modifier.padding(
        start=commonPadding,
        end= commonPadding,
        bottom= 4.dp),
        style = TextStyle(
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium
        )
    )
    OutlinedTextField(
        value = input.value, onValueChange = {
            input.value = it
        },
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(
                    color = Gray1,
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal
                )
            )
        },
        shape = RoundedCornerShape(cornerSize),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = commonPadding,
                end = commonPadding,
            )
            .border(BorderStroke(2.dp, Pink), shape = RoundedCornerShape(cornerSize))
            .background(Color.White, shape = RoundedCornerShape(cornerSize)),
        textStyle = TextStyle(
            color = if (input.value.isNotEmpty()) Color.Black else Gray1,
            fontSize = 18.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )
}