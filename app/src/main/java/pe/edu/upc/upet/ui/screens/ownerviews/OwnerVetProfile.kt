package pe.edu.upc.upet.ui.screens.ownerviews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_vetClinics.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic
import pe.edu.upc.upet.feature_vets.data.repository.VetRepository
import pe.edu.upc.upet.feature_vets.domain.Vet
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.ownerviews.pets.ImageRectangle
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.poppinsFamily


@Composable
fun OwnerVetProfile(vetId: Int, navController: NavController){
    val vetRepository = remember { VetRepository() }
    var vet by remember { mutableStateOf<Vet?>(null) }
    val vetClinicRepository = remember { VeterinaryClinicRepository() }
    var vetClinic by remember { mutableStateOf<VeterinaryClinic?>(null) }

    LaunchedEffect(vetId) {
        vetRepository.getVetById(vetId){vett->
            vet = vett
        }

    }



    LaunchedEffect(vet?.clinicId) {
        vet?.let {
            vetClinicRepository.getVeterinaryClinicById(it.clinicId) { clinic ->
                vetClinic = clinic
            }
        }
    }

    vet?.let { vett ->
        Scaffold(topBar = { TopBar(navController = navController, title = "Vet Profile" )}) { paddingValues ->
            LazyColumn {
                item {
                    Column(modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                        .fillMaxSize()
                        .padding(10.dp, 10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    )
                    {
                          ImageRectangle(imageUrl = vett.imageUrl)

                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                                .background(Color.White)
                        ){
                            Column (modifier = Modifier
                                .padding(15.dp, 15.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ){
                                    Text(
                                        text = "Dr. " + vett.name,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 24.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(text = "Veterinary Behavioral" , color = Color.Gray)

                                    TextSemiBold(text = "Clinic Veterinary", color = Blue1)

                                    vetClinic?.let { Text(text = it.name) }

                                    TextSemiBold(text = "About", color = Blue1)

                                    Text(text = vett.description?:"")

                                    TextSemiBold(text = "Experience", color = Blue1)

                                    Text(text = vett.experience.toString() , color = Color.Gray)

                                    Row {
                                        Text(
                                            text = "Reviews",
                                            style = TextStyle(
                                                color = Blue1,
                                                fontSize = 20.sp,
                                                fontFamily = poppinsFamily,
                                                fontWeight = FontWeight.SemiBold
                                            ),
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = "See all",
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier =
                                                Modifier.clickable {
                                                    navController.navigate(Routes.VetReviews.createRoute(vetId))
                                            })
                                    }

                                    ReviewCard()
                                    Spacer(modifier = Modifier.height(15.dp))
                                    CustomButton(text = "Book Appointment") {
                                        navController.navigate(Routes.BookAppointment.createRoute(vetId))
                                    }

                                    CustomButton(text = "See location") {
                                        //navController.navigate()
                                    }

                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun ProfileImage(url: String,size: Int,width: Int ){
    GlideImage(modifier = Modifier.size(width = width.dp, height = size.dp),imageModel = { url })
}

@Composable
fun ReviewCard(){
    Card(modifier = Modifier
        .shadow(elevation = 8.dp, shape = RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White)
    ) {
        Column (modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
        ){
            Row (modifier = Modifier.padding(horizontal = 7.dp,vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically){
                Box(modifier = Modifier.clip(shape = RoundedCornerShape(20.dp)) )
                {
                    ProfileImage(url = "https://media-cdn.tripadvisor.com/media/photo-s/12/0b/28/0e/para-compartir.jpg", 42,40)
                }
                Text(
                    text = "Jane Cooper",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 15.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f)
                )
                Column {
                    Row (modifier = Modifier.padding(start= 50.dp)){
                        Icon(imageVector = Icons.Default.Star, contentDescription = "Estrella", tint = Color(0xFFFFB800))
                        Text(text = "5")
                    }
                    Text(text = "2 min ago")
                }

            }
            Text(text = "Ullamco tempor adpisicsing et voluptate duis sit asd esse aliquea esse ex",color = Color.Gray)
        }
    }

}

@Composable
fun TextSemiBold(text: String, color: Color = MaterialTheme.colorScheme.primary) {
    Text(
        text = text,
        style = TextStyle(
            color = color,
            fontSize = 20.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold
        ),
    )
}