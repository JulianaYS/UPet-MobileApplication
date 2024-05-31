package pe.edu.upc.upet.ui.screens.vets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_appointment.data.repository.AppointmentRepository
import pe.edu.upc.upet.feature_appointment.domain.Appointments
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.feature_vets.data.repository.VetRepository
import pe.edu.upc.upet.ui.shared.SimplePetCard
import pe.edu.upc.upet.utils.TokenManager.getUserIdAndRoleFromToken


@Composable
fun VetHome(navController: NavController){

    Scaffold {
        paddingValues ->
        LazyColumn(modifier = Modifier
            .padding(paddingValues)
            .background(Color(0xFF0B1C3F))
            .fillMaxSize()) {
            item{ VeterinarySection() }
            item{}
            item{}
        }
    }
}
@Composable
fun VeterinarySection(){
    val name = remember{ mutableStateOf("")}
    val icon = remember { mutableStateOf("")}
    val place = remember{ mutableStateOf("")}

    val (id,userRol, _) = getUserIdAndRoleFromToken()?: error("Error obteniendo el userId y userRole desde ek token");

    VetRepository().getVetById(id){
        vetResponse->
        name.value = vetResponse?.name?:""
        icon.value = vetResponse?.image_url?:""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row(verticalAlignment = Alignment.CenterVertically){
            GlideImage(modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(50.dp))
                .padding(5.dp),
                imageModel = {"https://cdn-icons-png.freepik.com/512/8742/8742495.png"}
                )
            Column(modifier = Modifier.padding(start = 20.dp)) {
                val greeting = "Hello, Dra.${name.value}"
                Text(
                    text = greeting,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White

                )
                Text(
                    text = "Palacio",
                    color = Color.White
                )
            }
            Text(text = "",
                modifier = Modifier.padding(horizontal = 40.dp))

            Button(onClick = { /*TODO*/ },
                modifier = Modifier.background(Color.Transparent)) {
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
fun ListOfPets(navController: NavController){
    val petRepository = remember { PetRepository() }
    val vetRepository = remember {
        VetRepository()
    }
    val appointmentRepository = remember {
        AppointmentRepository()
    }
    var pets: List<PetResponse> by remember { mutableStateOf(emptyList())}
    val userId = getUserIdAndRoleFromToken()?.first
    if (userId != null)
    {
        vetRepository.getVetsByUserId(userId,
            callback ={
                vetObject->{
                    if(vetObject!=null){
                        appointmentRepository
                            .getAppointmentsByVetId(vetObject.id,callback = {
                                appointments ->
                                appointments.map {
                                    item->{

                                }
                                }
                        })
                    }
            }
            })
    }


    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        item(pets.take(4)){
            pet->
            PetItem(pet, navController){
                navController.navigate("PetProfile/${pet.id}")
            }
        }

    }


}
@Composable
fun PetItem(pet: Pet, navController: NavController){

    SimplePetCard(pet,navController ) {

    }


}