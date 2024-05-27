package pe.edu.upc.upet.ui.screens.pets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.material.icons.filled.WbSunny
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
import pe.edu.upc.upet.ui.shared.CustomButton
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
                    Row (modifier = Modifier.fillMaxWidth()
                        .padding(start = 20.dp,end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        PetProfileInformation("Breed", petValue.breed, Icons.Filled.Pets)
                        PetProfileInformation("Specie", petValue.species, Icons.Filled.WbSunny)
                    }
                    Row (modifier = Modifier.fillMaxWidth()
                        .padding(start= 20.dp,end= 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        PetProfileInformation("Weight", petValue.weight.toString(), Icons.Filled.Balance)
                        PetProfileInformation("Age", petValue.birthdate, Icons.Filled.Schedule)
                    }

                    Text(
                        text = "More details",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(top =10.dp, bottom = 10.dp)
                    )

                    CustomButton(text = "Add Information") {}
                }
            }
        }
    }
}

@Composable
fun PetProfileInformation(title: String, value: String, balance: ImageVector) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Start)
        )
        Row(
            modifier = Modifier.align(Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = balance,
                "Icon",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = value,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp)
            )
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