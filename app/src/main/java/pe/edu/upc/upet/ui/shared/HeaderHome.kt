package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HeaderHome(imageUrl: String, name: String, secondaryText: String, isItalic: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GlideImage(modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(50.dp)),
                imageModel = { imageUrl },
            )
            Column(modifier = Modifier.padding(start = 20.dp)) {
                val greeting = "Hello, $name"
                Text(
                    text = greeting,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                if (isItalic) Text(
                text = secondaryText,
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodyMedium
                ) else Text(
                text = secondaryText,
                fontSize = 16.sp,
                fontStyle = FontStyle.Normal,
                style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications",
            tint = Color.White
        )
    }
}
