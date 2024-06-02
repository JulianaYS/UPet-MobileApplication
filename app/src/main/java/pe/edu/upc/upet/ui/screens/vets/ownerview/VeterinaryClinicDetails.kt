package pe.edu.upc.upet.ui.screens.vets.ownerview

import android.util.Log
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_vetClinics.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic
import pe.edu.upc.upet.feature_vets.data.repository.VetRepository
import pe.edu.upc.upet.feature_vets.domain.Vet
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.theme.Blue1
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeterinaryClinicDetailsScreen(navController: NavHostController, vetClinicId: Int) {
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
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Blue1,
                        titleContentColor = Color.White
                    ),
                    title = {
                        Text("Veterinary Clinic Details")
                    },
                    navigationIcon = {
                        CustomReturnButton(navController = navController)
                    }
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    ClinicImage(imageUrl = veterinaryClinic.image_url)

                    ClinicNameAndRating(name = veterinaryClinic.name)

                    ClinicInfo(
                        phoneNumber = "987654321",
                        email = veterinaryClinic.name.lowercase(Locale.ROOT) + "@" + "gmail.com",
                        operatingHours = veterinaryClinic.office_hours_start + " - " + veterinaryClinic.office_hours_end,
                        location = veterinaryClinic.location
                    )

                    //DoctorsSection(navController, vets = vets ?: emptyList())

                    AboutUsSection(description = veterinaryClinic.description)

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomButton(text = "Book Appointment") {
                        navController.navigate(Routes.BookAppointmentScreen+"/"+vetClinicId)

                    }
                }
            }

        )

    }


}

@Composable
fun ClinicImage(imageUrl: String) {
    GlideImage(
        imageModel = { imageUrl },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp)),
        imageOptions = ImageOptions(contentScale = ContentScale.Crop)
    )
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
    Column(modifier = Modifier.padding(top = 8.dp)) {
        InfoRow(icon = Icons.Filled.Phone, text = phoneNumber)
        InfoRow(icon = Icons.Filled.Email, text = email)
        InfoRow(icon = Icons.Filled.WatchLater, text = "Operating Hours: $operatingHours")
        InfoRow(icon = Icons.Filled.LocationOn, text = "Location: $location")
    }
}
@Composable
fun DoctorsSection(navController: NavHostController, vets: List<Vet>) {
    Column {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Doctors (${vets.size})",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
        LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
            items(vets.take(4)) { vet ->
                SimpleVetCard(vet) {
                    navController.navigate("VetProfile/${vet.id}")
                }
            }
        }
    }
}

@Composable
fun SimpleVetCard(vet: Vet, onVetSelected: (Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blue1,
        ),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onVetSelected(vet.id) }
    ) {
        Log.d("SimpleVetCard", "SimpleVetCard: ${vet.image_url}")

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GlideImage(
                imageModel = { vet.image_url },
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp)),
                imageOptions = ImageOptions(contentScale = ContentScale.Crop)
            )
            Text(
                text = vet.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ServicesSection(services: String) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = "Services:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        services.forEach { service ->
            Text(
                text = "- $service",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}

@Composable
fun AboutUsSection(description:String) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = "About Us:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun InfoRow(icon: ImageVector?, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}



