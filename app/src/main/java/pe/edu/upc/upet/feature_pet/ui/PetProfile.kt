package pe.edu.upc.upet.feature_pet.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_pet.domain.Pet

@Composable
fun PetProfile() {
    Scaffold {paddingValues ->
        //val pet = Pet(1, "Boby", 3, "Golden Retriever")

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
                CustomReturnButton()
                Text(
                    text = "Boby",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(start = 80.dp)
                        .align(Alignment.CenterVertically)

                )
                IconButton(
                    modifier = Modifier.weight(1f).padding(start = 80.dp),
                    onClick = { }) {
                    Icon(
                        Icons.Filled.Male,
                        "Male",
                    )
                }
            }

            PetImage( "https://www.animalfiel.com/wp-content/uploads/2019/03/colores-de-chihuahua.jpg")

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
                                .padding(end = 80.dp)
                                .align(Alignment.CenterVertically)
                        )
                        IconButton(
                            modifier = Modifier.weight(1f),
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
                        PetProfileInformation("Specie", "pet.speci", Icons.Filled.Favorite)
                        PetProfileInformation("Breed", "pet.breed", Icons.Filled.Pets)
                    }
                    Row (modifier = Modifier.fillMaxWidth()
                        .padding(start= 20.dp,end= 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        PetProfileInformation("Weight", "pet.weigh", Icons.Filled.Balance)
                        PetProfileInformation("Age", "pet.age", Icons.Filled.Schedule)
                    }

                    Text(
                        text = "More details",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = Color.Black
                    )

                    CustomButton(text = "Add Information") {}


                }


            }


        }

    }
}

@Composable
fun CustomReturnButton() {
    IconButton(
        modifier = Modifier
            .background(Color(0xFFFF6262), shape = CircleShape),
        onClick = { /*TODO*/ }) {
        Icon(
            Icons.Filled.ArrowBackIosNew,
            "Back",
            tint = Color.White
        )
    }
}

@Composable
fun InputTextField(input: MutableState<String>, placeholder: String) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(16.dp)), // Rounded corners
        placeholder = {
            Text(text = placeholder, color = Color(0xFFB3B3B3))
        },
        value = input.value,
        onValueChange = {
            input.value = it
        }
    )
}

@Composable
fun CustomButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFF6262), shape = RoundedCornerShape(12.dp)),
        onClick = onClick
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
        )
    }
}

@Composable
fun PetImage(url: String) {
    GlideImage(modifier = Modifier
        .background(Color(0xFF0B1C3F))
        .height(height = 150.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .wrapContentSize(Alignment.Center)
        , imageModel = { url })
}

@Composable
fun PetProfileInformation(infoText: String, petInfoText: String, icon: ImageVector) {
    Card (
        modifier = Modifier

            .size(170.dp, 145.dp)
            .padding(start= 10.dp,top= 10.dp,end= 10.dp, bottom =  10.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .border(1.dp, Color.White, RoundedCornerShape(14.dp))
            .shadow(8.dp, shape = RoundedCornerShape(14.dp))

    ){
        Surface(color = Color.White) {
            Column {
                Row (modifier = Modifier.fillMaxWidth()
                    .padding(10.dp, 10.dp, 10.dp, 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = infoText,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        icon,
                        infoText,
                        tint = Color.Black)
                }
                Text(
                    text = petInfoText,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color(0xFFFF6262),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(top = 18.dp, bottom = 30.dp)
                )
            }
        }


    }
}