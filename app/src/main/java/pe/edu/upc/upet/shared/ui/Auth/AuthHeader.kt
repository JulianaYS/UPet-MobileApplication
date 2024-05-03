package pe.edu.upc.upet.shared.ui.Auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.R

import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun AuthHeader(texto: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = texto,
            modifier = Modifier.padding(
                top = 16.dp,
            ).fillMaxWidth(),
            style = TextStyle(
                color = Color.White,
                fontFamily = poppinsFamily,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
        Image(
            painter = painterResource(id = R.drawable.pet_logo),
            contentDescription = null,
            modifier = Modifier
                .height(223.dp)
                .width(217.dp),
            contentScale = ContentScale.Fit
        )
    }
}