package pe.edu.upc.upet.ui.screens.vets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.theme.Blue1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VetList(navController: NavController) {

    val vetClinicRepository = remember { VeterinaryClinicRepository() }
    var vetClinics: List<VeterinaryClinic> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(key1 = vetClinicRepository) {
        vetClinicRepository.getAllVeterinaryClinics { vetClinicsList ->
            vetClinics = vetClinicsList
        }
    }

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
        }
    ) {paddingValues->
        Column(modifier = Modifier.padding(paddingValues)) {
            vetClinics.forEach { vetClinic ->
                VetCard(navController, vetClinic)
            }
        }
    }
}


@Composable
fun VetCard(navController: NavController, veterinaryClinic: VeterinaryClinic) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp) .clickable {
                navController.navigate("VetDetails/${veterinaryClinic.id}")
            }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
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
                    text = "Operating Hours: ${veterinaryClinic.office_hours}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}