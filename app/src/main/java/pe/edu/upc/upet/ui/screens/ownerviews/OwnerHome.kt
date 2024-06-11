package pe.edu.upc.upet.ui.screens.ownerviews

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.feature_vetClinics.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.SimplePetCard
import pe.edu.upc.upet.ui.theme.PinkStrong
import pe.edu.upc.upet.utils.TokenManager.getUserIdAndRoleFromToken

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OwnerHome(navController: NavController){
    Log.d("OwnerHome", "OwnerHome")
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { UserSection() }
        item { PetsSection(navController) }
        item { RecommendedVetsSection(navController) }
    }
}



@Composable
fun UserSection() {
    val owner = getOwner() ?: return
    Log.d("UserSection", "Owner: $owner")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GlideImage(modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(50.dp)),
                imageModel = { owner.imageUrl },
            )
            Column(modifier = Modifier.padding(start = 20.dp)) {
                val greeting = "Hello, ${owner.name}"
                Text(
                    text = greeting,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                Text(
                    text = "Welcome!",
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
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PetsSection(navController: NavController) {
    val petRepository = remember { PetRepository() }
    var pets: List<Pet> by remember { mutableStateOf(emptyList()) }

    val ownerId = getUserIdAndRoleFromToken()?.first

    if (ownerId != null) {
        petRepository.getPetsByOwnerId(ownerId){
            pets = it
        }
    }

    Column {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "My Pets (${pets.size})",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
            Text(
                text = "Add a pet",
                style = MaterialTheme.typography.bodyMedium,
                color = PinkStrong,
                modifier = Modifier
                    .clickable {  navController.navigate(Routes.RegisterPet.route) }
            )

        }
        LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
            items(pets.take(4)) { pet ->
                SimplePetCard(pet) {
                    navController.navigate(Routes.PetDetails.createRoute(pet.id))
                }
            }
        }

    }
}



@Composable
fun RecommendedVetsSection(navController: NavController) {
    val vetClinicRepository = remember { VeterinaryClinicRepository() }
    var vetClinics: List<VeterinaryClinic> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(key1 = vetClinicRepository) {
        vetClinicRepository.getAllVeterinaryClinics { vetClinicsList ->
            vetClinics = vetClinicsList
        }
    }

    Column {
        Text(
            text = "Recommended Veterinary Clinics",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        Column {
            vetClinics.forEach { vetClinic ->
                VetClinicCard(navController,vetClinic)
                Spacer(modifier = Modifier.height(22.dp))
            }
        }
    }
}

