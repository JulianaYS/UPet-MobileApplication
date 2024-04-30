package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable

fun SearchField() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White) },
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp).clip(RoundedCornerShape(20.dp))
            .border(2.dp, Color(0xFFE91E63), RoundedCornerShape(20.dp))
    )

}
