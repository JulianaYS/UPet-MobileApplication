package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun CustomReturnButton(navController: NavController) {
    IconButton(
        modifier = Modifier
            .background(Color(0xFFFF6262), shape = CircleShape),
        onClick = {navController.popBackStack() }) {
        Icon(
            Icons.Filled.ArrowBackIosNew,
            "Back",
            tint = Color.White
        )
    }
}