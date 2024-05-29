package pe.edu.upc.upet.ui.screens.pets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.material.icons.outlined.Balance
import androidx.compose.material.icons.outlined.Dehaze
import androidx.compose.material.icons.outlined.MedicalInformation
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Vaccines
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import pe.edu.upc.upet.feature_pet.data.remote.GenderEnum
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.utils.TokenManager

@Composable
fun PetProfile(petId: Int?, navController: NavController) {
    val pet = remember { mutableStateOf<PetResponse?>(null) }

   val ownerId = TokenManager.getUserIdAndRoleFromToken()?.first ?: 0

    PetRepository().getPetsByOwnerId(ownerId ?: 0, onSuccess = {
            if(petId != null) {
                pet.value = it.find { pet -> pet.id == petId }
            }
            Log.d("g", "PetProfile: $it") }, onError = {Log.d("orrorregister", "") })


    val petValue = pet.value ?: PetResponse(0, "", 0, "", "", 0.0f, "",  "", GenderEnum.Male)

    //cambiar esto
    data class PetInfo(val title: String, val icon: ImageVector, val content: String)
    fun petResponseToPetInfoList(petResponse: PetResponse): List<PetInfo> {
        return listOf(
            PetInfo("Breed", Icons.Outlined.Pets, petResponse.breed),
            PetInfo("Species", Icons.Outlined.WbSunny, petResponse.species),
            PetInfo("Weight", Icons.Outlined.Balance, petResponse.weight.toString()),
            PetInfo("Birthdate", Icons.Outlined.Timer, petResponse.birthdate),
        )
    }
    val petInfoList = petResponseToPetInfoList(petValue)

    //esto tbm
    data class MedicalHistory(
        val type: String,
        val date: String,
        val description: String,
        val icon: ImageVector
    )
    val medicalHistoryList = listOf(
        MedicalHistory("Diagnosis", "14/04/2024", "The animal presents high corporal temperature 1 hour ago. Also manifests stomach...", Icons.Outlined.MedicalServices),
        MedicalHistory("Results", "15/04/2024", "The animal's temperature has returned to normal. Stomach issues have been resolved.", Icons.Outlined.Dehaze),
        MedicalHistory("Vaccines", "16/04/2024", "The animal has been vaccinated against rabies.", Icons.Outlined.Vaccines),
        MedicalHistory("Surgery", "17/04/2024", "The animal has undergone a successful minor surgery.", Icons.Outlined.MedicalInformation)
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxSize()
                .padding(10.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){
                CustomReturnButton(navController = navController)
                Text(
                    text = petValue.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )

                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {  }) {
                    Icon(
                        imageVector = if(petValue.gender == GenderEnum.Male) Icons.Filled.Male else Icons.Filled.Female,
                        contentDescription = petValue.gender.toString()
                    )
                }
            }
            println("Gender: ${petValue.gender}")
            PetImage(petValue.image_url)

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
            ){
                Column (modifier = Modifier.padding(15.dp, 15.dp)){
                    Row (modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = "General Information",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            onClick = { }) {
                            Icon(
                                Icons.Filled.TagFaces,
                                "TagFaces",
                                tint = Color.Black
                            )
                        }
                    }

                    LazyRow {
                        items(petInfoList) { petInfo ->
                            PetInformationCard(petInfo.title, petInfo.icon, petInfo.content)
                        }
                    }

                    Text(
                        text = "More details",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(top =10.dp, bottom = 10.dp)
                    )
                    LazyColumn {
                        items(medicalHistoryList) { medicalHistory ->
                            MedicalHistoryCard(medicalHistory.type, medicalHistory.date, medicalHistory.description, medicalHistory.icon)
                        }
                    }
/*
                    CustomButton(text = "EditProfile") {
                        navController.navigate("PetEdit/${petValue.id}")
                    }*/
                }
            }
        }
    }
}

@Composable
fun PetImage(imageUrl: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "Pet Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp)
                .clip(shape = RoundedCornerShape(20.dp))
        )
    }
}

@Composable
fun PetInformationCard(title:String, icon:ImageVector,content:String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .width(180.dp)
            .height(100.dp)
            .padding(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color(0xFF0A2540)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Icon(icon, contentDescription = "heart", tint = Color(0xFF0A2540))
        }
        Text(
            text = content,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .align(Alignment.CenterHorizontally),
        )
    }
}


@Composable
fun MedicalHistoryCard(title: String, date: String, description: String,icon: ImageVector) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp)
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF0A2540),
                contentColor = Color.White
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFFFF6D6D), shape = RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector =icon,
                        contentDescription = "Medical services",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Content Column
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    // Title and Date Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = date,
                            fontSize = 14.sp,
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    // Description
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
