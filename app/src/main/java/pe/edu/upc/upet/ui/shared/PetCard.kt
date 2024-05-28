package pe.edu.upc.upet.ui.shared

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import pe.edu.upc.upet.feature_pet.domain.Pet

@Composable
fun PetCard( navController: NavHostController, pet: PetResponse) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Log.d("PetCard", "Pet Image URL: ${pet.image_url}")


            GlideImage(imageModel = { pet.image_url },
                modifier = Modifier
                    .padding(10.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = pet.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.Black
                    )
                    Text(
                        text = "Breed: ${pet.breed}",
                        color = Color.Gray
                    )
                    Text(
                        text = "Age: ${pet.birthdate}",
                        color = Color.Gray
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { navController.navigate("PetProfile/${pet.id}")},
                        colors = ButtonDefaults.buttonColors(Color(0xFFEB5569)),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text("View")
                    }
                    Button(
                        onClick = { navController.navigate("PetEdit/${pet.id}")},
                        colors = ButtonDefaults.buttonColors(Color(0xFFEB5569)),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text("Edit")
                    }
                }
            }
        }
    }
}