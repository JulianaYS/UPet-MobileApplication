package pe.edu.upc.upet.shared.ui.Auth

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.UpetOrange1
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun AuthCheckBox(checkedState: MutableState<Boolean>){
    Row(
        modifier = Modifier
            .padding(
                start = BorderPadding,
                end = BorderPadding,
                bottom = 4.dp
            )
            .fillMaxWidth()
    ) {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
            colors = CheckboxDefaults.colors(
                checkmarkColor = Color.Black, // Color del check
                checkedColor = Color.White, // Color de la casilla cuando está seleccionada
                uncheckedColor = Color.White,
                disabledUncheckedColor = Color.White,
                disabledCheckedColor = Color.White,
                disabledIndeterminateColor = Color.White
            ),
        )

        val termsAndConditions = "Terms & Conditions"
        val privacyPolicy = "Privacy Policy"
        val text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append("I agree to the ")
            }
            withStyle(style = SpanStyle(color = UpetOrange1)) { // Color azul para los enlaces
                append(termsAndConditions)
            }
            withStyle(style = SpanStyle(color = Color.White)) {
                append(" and ")
            }
            withStyle(style = SpanStyle(color = UpetOrange1)) { // Color azul para los enlaces
                append(privacyPolicy)
            }
        }

        ClickableText(
            text = text,
            onClick = { offset ->
                // Implementa aquí la lógica para manejar el clic en los enlaces
                if (offset in text.indexOf(termsAndConditions) until text.indexOf(termsAndConditions) + termsAndConditions.length) {
                    // Hacer algo cuando se hace clic en "Terms & Conditions"
                } else if (offset in text.indexOf(privacyPolicy) until text.indexOf(privacyPolicy) + privacyPolicy.length) {
                    // Hacer algo cuando se hace clic en "Privacy Policy"
                }
            },
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}