package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.UpetGray1
import pe.edu.upc.upet.ui.theme.UpetOrange1
import pe.edu.upc.upet.ui.theme.poppinsFamily
@Composable
fun AuthInputTextField(
    input: MutableState<String>,
    placeholder: String,
    label: String,
    isPassword: Boolean = false
) {
    val commonPadding = BorderPadding
    val cornerSize = 10.dp

    val isPasswordVisible = remember {
        mutableStateOf(false)
    }
    LabelTextField(label, commonPadding)

    CustomOutlinedTextField(
        input = input,
        placeholder = placeholder,
        isPassword = isPassword,
        isPasswordVisible = isPasswordVisible,
        cornerSize = cornerSize
    )
}


@Composable
fun LabelTextField(label: String, commonPadding: Dp){
    Text(
        text = label,
        modifier = Modifier.padding(
            start = commonPadding,
            end = commonPadding,
            bottom = 4.dp
        ),
        style = TextStyle(
            color = Color.White,
            fontSize = 12.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium
        )
    )
}

@Composable
fun TextPlaceHolder(placeholder: String){
    Text(
        text = placeholder,
        style = TextStyle(
            color = UpetGray1,
            fontSize = 12.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal
        )
    )

}

@Composable
private fun CustomOutlinedTextField(
    input: MutableState<String>,
    placeholder: String,
    isPassword: Boolean,
    isPasswordVisible: MutableState<Boolean>,
    cornerSize: Dp
) {
    OutlinedTextField(
        value = input.value,
        onValueChange = { input.value = it },
        placeholder = {
            TextPlaceHolder(placeholder)
        },
        shape = RoundedCornerShape(cornerSize),
        modifier = Modifier
            .fillMaxWidth()
            .size(height = 56.dp, width = 300.dp)
            .padding(bottom = 10.dp, start= BorderPadding, end= BorderPadding)
            .border(BorderStroke(2.dp, UpetOrange1), shape = RoundedCornerShape(cornerSize))
            .background(Color.White, shape = RoundedCornerShape(cornerSize)),
        textStyle = TextStyle(
            color = if (input.value.isNotEmpty()) Color.Black else UpetGray1,
            fontSize = 12.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal
        ),
        keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text),
        visualTransformation = if (isPassword) {
            if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
        } else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { isPasswordVisible.value = !isPasswordVisible.value }) {
                    Icon(
                        imageVector = if (isPasswordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isPasswordVisible.value) "Hide password" else "Show password",
                        tint = UpetOrange1
                    )
                }
            }
        }
    )
}