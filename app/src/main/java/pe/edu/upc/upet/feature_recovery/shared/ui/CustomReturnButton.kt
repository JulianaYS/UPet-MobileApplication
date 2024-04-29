package pe.edu.upc.upet.feature_recovery.shared.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CustomReturnButton() {
    IconButton(
        modifier = Modifier
            .background(Color(0xFFFF6262), shape = CircleShape),
        onClick = { /*TODO*/ }) {
        Icon(
            Icons.Filled.ArrowBackIosNew,
            "Back",
            tint = Color.White
        )
    }
}