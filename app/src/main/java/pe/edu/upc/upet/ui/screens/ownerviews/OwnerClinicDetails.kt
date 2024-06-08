package pe.edu.upc.upet.ui.screens.ownerviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_vetClinics.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic
import pe.edu.upc.upet.feature_vets.data.repository.VetRepository
import pe.edu.upc.upet.feature_vets.domain.Vet
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.ownerviews.pets.ImageRectangle
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.poppinsFamily
import java.util.Locale

@Composable
fun OwnerClinicDetails(navController: NavHostController, vetClinicId: Int) {
    val vetClinicRepository = remember { VeterinaryClinicRepository() }
    val vetRepository = remember { VetRepository() }
    var vetClinic by remember { mutableStateOf<VeterinaryClinic?>(null) }
    var vets by remember { mutableStateOf<List<Vet>?>(null) }

    LaunchedEffect(key1 = vetClinicId) {
        vetClinicRepository.getVeterinaryClinicById(vetClinicId) { clinic ->
            vetClinic = clinic
        }
    }

    LaunchedEffect(key1 = vetClinicId) {
        vetRepository.getVetsByClinicId(vetClinicId) { vetList ->
            vets = vetList
        }
    }

    vetClinic?.let { veterinaryClinic ->
        Scaffold(
            topBar = {
                TopBar(navController = navController, title = "Veterinary Clinic Details")
            },
            modifier = Modifier.padding(16.dp),
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    ImageRectangle(imageUrl = veterinaryClinic.image_url)

                    ClinicNameAndRating(name = veterinaryClinic.name)

                    ClinicInfo(
                        phoneNumber = "987654321",
                        email = veterinaryClinic.name.lowercase(Locale.ROOT) + "@" + "gmail.com",
                        operatingHours = veterinaryClinic.office_hours_start + " - " + veterinaryClinic.office_hours_end,
                        location = veterinaryClinic.location
                    )

                    AboutUsSection(description = veterinaryClinic.description)

                    TextSemiBold(text = "Our Specialists")

                    LazyRow {
                        items(vets ?: emptyList()) { vet ->
                            VeterinaryCard(vet, navController)
                        }
                    }

                    CustomButton(text = "Book Appointment") {
                        navController.navigate(Routes.BookAppointment.createRoute(vetClinicId))
                    }

                }
            }
        )
    }
}

@Composable
fun ClinicNameAndRating(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Row {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Rating",
                tint = Color.Yellow
            )
            Text(text = "4.5")
        }
    }
}

@Composable
fun ClinicInfo(phoneNumber: String, email: String, operatingHours: String, location: String) {
    Column(modifier = Modifier.padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        InfoRow(icon = Icons.Filled.Phone, text = phoneNumber)
        InfoRow(icon = Icons.Filled.Email, text = email)
        InfoRow(icon = Icons.Filled.WatchLater, text = "Operating Hours: $operatingHours")
        InfoRow(icon = Icons.Filled.LocationOn, text = "Location: $location")
    }
}

@Composable
fun AboutUsSection(description:String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextSemiBold(text = "About us:")
        TextNormal(text = description, color = Color.White)
    }
}

@Composable
fun InfoRow(icon: ImageVector?, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
        TextNormal(text = text, color= Color.White)
    }
}

@Composable
fun TextNormal(text: String, color: Color = Color.Gray) {
    Text(
        text = text,
        style = TextStyle(
            color = color,
            fontSize = 15.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal
        ),
    )
}

@Composable
fun VeterinaryCard(vet: Vet, navController: NavHostController) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .width(100.dp)
            .clickable {
                navController.navigate(Routes.OwnerVetProfile.createRoute(vet.id))
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                imageModel = { vet.imageUrl },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                imageOptions = ImageOptions(contentScale = ContentScale.Crop)
            )
            TextSemiBold(text = vet.name)
            TextNormal(text = vet.name, color = Color.Black)
        }
    }
}


