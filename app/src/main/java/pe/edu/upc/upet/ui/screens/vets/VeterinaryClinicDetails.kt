package pe.edu.upc.upet.ui.screens.vets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeterinaryClinicDetailsScreen(navController: NavHostController, vetClinicId: Int) {
    val vetClinicRepository = remember { VeterinaryClinicRepository() }
    var vetClinic: VeterinaryClinic? by remember { mutableStateOf(null) }

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
                        titleContentColor = Color.White,
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
                            .clip(RoundedCornerShape(20.dp)),
                        imageOptions = ImageOptions(contentScale = ContentScale.Crop)
                    )

                    Text(
                        text = veterinaryClinic.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = veterinaryClinic.location,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = "Operating Hours: ${veterinaryClinic.office_hours}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = "Services:",
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
                        text = "Contact Information:",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    /*Text(
                    text = "Phone: ${veterinaryClinic}",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Email: ${veterinaryClinic.contact_email}",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 8.dp)
                )*/

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        CustomButton(onClick = { navController.navigate(Routes.BookAppointmentScreen)}, text = "Make an Appointment")
                        CustomButton(onClick = { }, text = "Call")
                        CustomButton(onClick = { }, text = "Email")
                    }

                    Button(
                        onClick = { /* View Location on Map */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    ) {
                        Text(text = "View Location on Map", color = Color.White)
                    }
                }
            }
        )
    }

}
