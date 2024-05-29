package pe.edu.upc.upet.ui.screens.vets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinics
import pe.edu.upc.upet.feature_vetClinics.domain.veterinaryClinics
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.theme.Blue1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VetList(navController: NavController) {

    val veterinaryClinics = veterinaryClinics
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
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(veterinaryClinics) { vet ->
                    VetCard(vet, onVetSelected = {
                        navController.navigate("vetProfile/${vet.id}")
                    })
                }

        }
    }
}


@Composable
fun VetCard(veterinaryClinics: VeterinaryClinics, onVetSelected: ()->Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
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