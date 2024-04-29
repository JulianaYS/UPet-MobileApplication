package pe.edu.upc.upet.feature_pet.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.feature_pet.domain.pets
import pe.edu.upc.upet.feature_vet.domain.VeterinaryClinics
import pe.edu.upc.upet.feature_vet.domain.veterinaryClinics

@Composable
fun Home(onBack: () -> Unit) {
    val backgroundColor = Color(0xFF0B1C3F)
    Surface(color = backgroundColor) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row( verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberImagePainter("https://img.freepik.com/foto-gratis/cabeza-hermosa-mujer-piel-oscura-expresion-feliz-tiene-peinado-afro-demuestra-dientes-blancos-perfectos-tiene-sonrisa-complacida-elegante-joven-afroamericana-descansa-interior_273609-2384.jpg?t=st=1714346926~exp=1714350526~hmac=9126533c34ec64fda3f2f543ac3bd74072727f80568fb53c226eac8503562d00&w=996"),
                        contentDescription = "User Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(50.dp))
                    )
                    Column(modifier = Modifier.padding(start = 20.dp)) {
                        Text(
                            text = "Hello, Monica",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                        Text(
                            text = "Good Morning",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White
                )
            }

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


            Text(
                text = "My Pets",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )

            LazyRow(modifier = Modifier.background(Color.Transparent)) {
                items(pets) { pet ->
                    PreviewPetCard(pet, backgroundColor)
                }
            }

            Text(
                text = "Best Veterinary Clinics Nearby",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(modifier = Modifier.background(Color.Transparent)) {
                items(veterinaryClinics) { clinic ->
                    VetCard(clinic)
                }
            }
        }
    }
}

@Composable
fun PreviewPetCard(pet: Pet, backgroundColor: Color) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
        modifier = Modifier.padding(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = rememberImagePainter(pet.imageUrl),
                contentDescription = "Pet Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Text(
                text = pet.name,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun VetCard(veterinaryClinics: VeterinaryClinics) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberImagePainter(veterinaryClinics.imageUrl),
                contentDescription = "Veterinary Clinic Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Column(modifier = Modifier.padding(start = 16.dp)) { // Añade este modificador
                Text(
                    text = veterinaryClinics.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Text(
                    text = veterinaryClinics.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = veterinaryClinics.contactNumber,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = "Operating Hours: ${veterinaryClinics.operatingHours}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}
