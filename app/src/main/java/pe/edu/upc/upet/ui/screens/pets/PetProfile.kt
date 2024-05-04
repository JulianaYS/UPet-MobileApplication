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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.feature_pet.domain.pets
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.CustomReturnButton

@Composable
fun PetProfile(petId: Int?, navController: NavHostController) {
    val pet = getPetById(petId)

    Scaffold {paddingValues ->
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
                if (pet != null) {
                    Text(
                        text = pet.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }

                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {  }) {
                    if (pet != null) {
                        Icon(
                            imageVector = if(pet.gender == "Male") Icons.Filled.Male else Icons.Filled.Female,
                            contentDescription = pet.gender
                        )
                    }
                }
            }

            if (pet != null) {
                PetImage(pet.image_url)
            }

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
                        if (pet != null) {
                            PetProfileInformation("Breed", pet.breed, Icons.Filled.Pets)
                        }
                        //Species
                        if (pet != null) {
                            PetProfileInformation("Specie", pet.specie, Icons.Filled.WbSunny)
                        }
                    }
                    Row (modifier = Modifier.fillMaxWidth()
                        .padding(start= 20.dp,end= 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        if (pet != null) {
                            PetProfileInformation("Weight", pet.weight.toString(), Icons.Filled.Balance)
                        }
                        if (pet != null) {
                            PetProfileInformation("Age", pet.age.toString(), Icons.Filled.Schedule)
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
            painter = rememberImagePainter(imageUrl),
            contentDescription = "Pet Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
        )
    }
}

fun getPetById(id: Int?): Pet? {
        return pets.find { it.id == id}
}