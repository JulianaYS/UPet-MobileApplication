package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.ui.theme.Pink

@Composable
fun CustomButton(text: String, icon: ImageVector? = null, onClick: () -> Unit, ) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .background(Pink, shape = RoundedCornerShape(10.dp)),
        onClick = onClick
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = "$text  Icon")
        }
        Text(
            text = text,
        )
    }
}