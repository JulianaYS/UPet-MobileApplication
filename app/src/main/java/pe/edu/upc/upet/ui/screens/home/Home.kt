package pe.edu.upc.upet.ui.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.rememberAsyncImagePainter
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.feature_profile.data.repository.PetOwnerRepository
import pe.edu.upc.upet.feature_vetClinics.domain.veterinaryClinics
import pe.edu.upc.upet.feature_vets.data.repository.VetRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.vets.VetCard
import pe.edu.upc.upet.ui.shared.SearchField
import pe.edu.upc.upet.ui.shared.SimplePetCard
import pe.edu.upc.upet.ui.theme.PinkStrong
import pe.edu.upc.upet.utils.TokenManager.getUserIdAndRoleFromToken

@Composable
fun Home( navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize()
        .size(100.dp)) {
        UserSection()
        SearchField()
        PetsSection(navController)
        RecommendedVetsSection(navController)
    }
}

@Composable
fun UserSection() {

    val name = remember { mutableStateOf("") }
    val icon = remember { mutableStateOf("") }

     val (id, userRol, _) = getUserIdAndRoleFromToken() ?: error("Error obteniendo el userId y userRole desde el token")

    if(userRol == "Vet"){
        VetRepository().getVetById(id) { vetResponse ->
            name.value = vetResponse?.name?:""
            icon.value = vetResponse?.image_url?:""
        }
    }else{
        PetOwnerRepository().getPetOwnerById(id) { petOwnerResponse ->
            name.value = petOwnerResponse?.name?:""
            icon.value = petOwnerResponse?.imageUrl?:""
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(icon.value?:"https://cdn-icons-png.freepik.com/512/8742/8742495.png"),
                contentDescription = "User Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(50.dp))
            )
            Column(modifier = Modifier.padding(start = 20.dp)) {
                val greeting = "Hello, ${name.value}"
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


@Composable
fun PetsSection(navController: NavController) {

    val petRepository = remember { PetRepository() }
    var pets: List<PetResponse> by remember { mutableStateOf(emptyList()) }

    val ownerId = getUserIdAndRoleFromToken()?.first

    if (ownerId != null) {
        petRepository.getPetsByOwnerId(ownerId,
            onSuccess = { petsList ->
                Log.d("PetsSection", "Pets list received: $petsList")

                pets = petsList.map { petResponse ->
                    PetResponse(
                        id = petResponse.id,
                        name = petResponse.name,
                        petOwnerId = petResponse.petOwnerId,
                        breed = petResponse.breed,
                        species = petResponse.species,
                        weight = petResponse.weight,
                        birthdate = petResponse.birthdate,
                        image_url = petResponse.image_url,
                        gender = petResponse.gender
                    )
                }
            },
            onError = {
            }
        )
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
                    .clickable {  navController.navigate(Routes.registerPet) }
            )

        }
        LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
            items(pets.take(4)) { pet ->
                SimplePetCard(pet, navController) {
                    navController.navigate("PetProfile/${pet.id}")
                }
            }
        }

    }
}



@Composable
fun RecommendedVetsSection(navController: NavController ) {
    Column {
        Text(
            text = "Recommended Veterinary Clinics",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn {
            items(veterinaryClinics) { vet ->
                VetCard(vet, onVetSelected = {navController.navigate("VetProfile/${vet.id}")})
            }

        }
    }
}

