package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(Color(0xFFFF6262), shape = RoundedCornerShape(10.dp)),
        onClick = onClick
    ) {
        Text(
            text = text,
        )
    }
}