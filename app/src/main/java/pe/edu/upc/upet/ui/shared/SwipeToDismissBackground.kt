package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SwipeToDismissBackground() {
    Box(
    modifier = Modifier
    .fillMaxSize()
    .background(Color(0xFFEB5569))
    .padding(horizontal = 15.dp),
    contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "Delete Pet",
            tint = Color.White
        )
    }
}