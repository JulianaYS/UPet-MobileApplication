package pe.edu.upc.upet.shared.ui.Auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.UPetTheme
import pe.edu.upc.upet.ui.theme.UpetOrange1
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun AuthButton(text: String){
    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(
            containerColor = UpetOrange1, contentColor = Color.White
      ),

        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth().padding(
            top = 4.dp,
            bottom = 4.dp,
            start = BorderPadding,
            end = BorderPadding,
        )
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold
            )
        )
    }

}

