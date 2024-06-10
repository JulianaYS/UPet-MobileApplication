package pe.edu.upc.upet.ui.screens.ownerviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_vetClinics.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.SearchField
import pe.edu.upc.upet.ui.shared.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerClinicList(navController: NavController) {

    val vetClinicRepository = remember { VeterinaryClinicRepository() }
    var vetClinics: List<VeterinaryClinic> by remember { mutableStateOf(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(key1 = vetClinicRepository) {
        vetClinicRepository.getAllVeterinaryClinics { vetClinicsList ->
            vetClinics = vetClinicsList
        }
    }

    Scaffold(
        topBar = {
            TopBar(navController = navController, title = "Veterinary Clinics")
        },
        modifier = Modifier.padding(16.dp)
    ) {paddingValues->
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchField(searchQuery = searchQuery, onValueChange = {
                searchQuery = it
            })
            vetClinics.filter { it.name.contains(searchQuery, ignoreCase = true) }.forEach { vetClinic ->
                Spacer(modifier = Modifier.height(18.dp))
               VetClinicCard(navController, vetClinic)

           }
        }
    }
}

@Composable
fun VetClinicCard(navController: NavController, veterinaryClinic: VeterinaryClinic) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth().padding(horizontal = 16.dp)
            .clickable {
                navController.navigate(Routes.OwnerClinicDetails.createRoute(veterinaryClinic.id))
            }
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            GlideImage(
                imageModel = { veterinaryClinic.image_url },
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp)),
                imageOptions = ImageOptions(contentScale = ContentScale.Crop)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) { // Añade este modificador
                Text(
                    text = veterinaryClinic.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Text(
                    text = veterinaryClinic.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = "Operating Hours: ${veterinaryClinic.office_hours_start + " - " + veterinaryClinic.office_hours_end}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}