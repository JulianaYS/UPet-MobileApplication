package pe.edu.upc.upet.ui.screens.vets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_appointment.data.repository.AppointmentRepository
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.feature_profile.data.repository.PetOwnerRepository
import pe.edu.upc.upet.feature_vets.data.repository.VetRepository
import pe.edu.upc.upet.utils.TokenManager.getUserIdAndRoleFromToken


@Composable
fun VetHome(navController: NavController) {
    val petOwnerRepository = remember {
        PetOwnerRepository()
    }
    val petRepository = remember { PetRepository() }
    val vetRepository = remember { VetRepository() }
    val appointmentRepository = remember { AppointmentRepository() }
    var pets by remember { mutableStateOf<List<Pair<PetResponse, String>>?>(null) }
    var appointments by remember { mutableStateOf<Map<Int, String>>(emptyMap()) }

    val (id, userRol, _) = getUserIdAndRoleFromToken() ?: error("Error obteniendo el userId y userRole desde el token")

    LaunchedEffect(Unit) {
        vetRepository.getVetsByUserId(id) { vetObject ->
            if (vetObject != null) {
                appointmentRepository.getAppointmentsByVetId(vetObject.id) { appointmentList ->
                    val petList = mutableListOf<Pair<PetResponse, String>>()
                    val appointmentDates = mutableMapOf<Int, String>()
                    val totalAppointments = appointmentList.size
                    var fetchedAppointments = 0

                    appointmentList.forEach { appointment ->
                        petRepository.getPetById(appointment.petId) { petResponse ->
                            petOwnerRepository.getPetOwnerById(petResponse.petOwnerId) { ownerResponse ->
                                if (ownerResponse != null) {
                                    petList.add(Pair(petResponse, ownerResponse.name))
                                }
                                appointmentDates[petResponse.id] = appointment.datetime
                                fetchedAppointments++
                                if (fetchedAppointments == totalAppointments) {
                                    pets = petList
                                    appointments = appointmentDates
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF0B1C3F))
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            item {
                VeterinarySection()
            }
            item {
                Text(
                    text = "Upcoming Appointments",
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            pets?.forEach { (pet, ownerName) ->
                item {
                    val appointmentDate = appointments[pet.id] ?: ""
                    PetCard(pet, ownerName, appointmentDate, navController) {
                        navController.navigate("PetProfile/${pet.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun VeterinarySection() {
    val name = remember { mutableStateOf("") }
    val icon = remember { mutableStateOf("") }
    val place = remember { mutableStateOf("") }

    val (id, userRol, _) = getUserIdAndRoleFromToken() ?: error("Error obteniendo el userId y userRole desde el token")

    VetRepository().getVetsByUserId(id) { vetResponse ->
        name.value = vetResponse?.name ?: ""
        icon.value = vetResponse?.image_url ?: ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GlideImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .padding(5.dp),
                imageModel = { icon.value }
            )
            Column(modifier = Modifier.padding(start = 20.dp)) {
                val greeting = "Hello, Dra.${name.value}"
                Text(
                    text = greeting,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                Text(
                    text = place.value,
                    color = Color.White
                )
            }
            Text(
                text = "",
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.background(Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notification",
                    tint = Color.White,
                    modifier = Modifier.background(Color.Transparent)
                )
            }
        }
    }
}

@Composable
fun PetCard(pet: PetResponse, ownerName: String, appointmentDate: String, navController: NavController, onPetSelected: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPetSelected(pet.id) }
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(pet.image_url),
                contentDescription = "Pet Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = "Name: ${pet.name}")
                Text(text = "Owner: $ownerName")
                Text(text = "Date: $appointmentDate")
            }
        }
    }
}
