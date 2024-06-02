package pe.edu.upc.upet.ui.screens.vets

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_vetClinics.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.theme.Blue1
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeterinaryClinicDetailsScreen(navController: NavHostController, vetClinicId: Int) {
    val vetClinicRepository = remember { VeterinaryClinicRepository() }
    var vetClinic by remember { mutableStateOf<VeterinaryClinic?>(null) }

    LaunchedEffect(key1 = vetClinicId) {
        vetClinicRepository.getVeterinaryClinicById(vetClinicId) { clinic ->
            vetClinic = clinic
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
                        Text("Veterinary Clinics")
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
                    GlideImage(
                        imageModel = { veterinaryClinic.image_url },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        imageOptions = ImageOptions(contentScale = ContentScale.Crop)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = veterinaryClinic.name,
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

                    Column(modifier = Modifier.padding(top = 8.dp)) {
                        InfoRow(icon = Icons.Filled.Phone, text = "987654321" ?: "Not Available")
                        InfoRow(icon = Icons.Filled.Email, text = veterinaryClinic.name.lowercase(Locale.ROOT) +"@" + "gmail.com")
                        InfoRow(icon = Icons.Filled.WatchLater, text = "Operating Hours: ${veterinaryClinic.office_hours_start + " - " + veterinaryClinic.office_hours_end}")
                        InfoRow(icon = Icons.Filled.LocationOn, text = "Location: ${veterinaryClinic.location}")
                    }

                    Text(
                        text = "Services:",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Column(modifier = Modifier.padding(top = 8.dp)) {
                        veterinaryClinic.services.forEach { service ->
                            Text(
                                text = "- $service",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    Text(
                        text = "About Us:",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = "We are a dedicated team of veterinary professionals committed to providing the highest quality care for your beloved pets. With years of experience and a passion for animal welfare, we offer a wide range of services to keep your furry friends happy and healthy.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                      Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CustomButton(onClick = { navController.navigate(Routes.BookAppointmentScreen+ "/${veterinaryClinic.id}") }, text = "Make an Appointment")
                        CustomButton(onClick = { /* Handle Map Click */ }, text = "View Location on Map")
                    }
                }
            }
        )
    }
}

// Helper Composable for displaying information rows
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