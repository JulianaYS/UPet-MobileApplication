package pe.edu.upc.upet.ui.shared

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import pe.edu.upc.upet.ui.theme.Blue1

@Composable
fun SimplePetCard(pet: PetResponse, onPetSelected: (Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blue1,
        ),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onPetSelected(pet.id) }
    ) {
        Log.d("SimplePetCard", "SimplePetCard: ${pet.image_url}")

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GlideImage(
                imageModel = { pet.image_url },
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp)),
                imageOptions = ImageOptions(contentScale = ContentScale.Crop)
            )
            Text(
                text = pet.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}