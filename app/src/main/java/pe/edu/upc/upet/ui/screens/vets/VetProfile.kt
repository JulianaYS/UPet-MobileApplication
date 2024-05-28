package pe.edu.upc.upet.ui.screens.vets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun VetProfile(navController: NavHostController) {

    val icon = remember { mutableStateOf("") }

    Scaffold { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {

            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .background(UpetBackGroundPrimary)
                            .padding(top = 10.dp, start = BorderPadding, end = BorderPadding),
                        verticalArrangement = Arrangement.spacedBy(13.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    )
                    {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF0B1C3F)),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .weight(0.1f)
                                    .padding(top = 8.dp, start = 16.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(Color.White)
                                    .size(45.dp)
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    "Back",
                                    modifier =  Modifier.fillMaxSize(1f),
                                    tint = Blue1
                                )
                            }
                            Text(
                                text = "Profile",
                                modifier = Modifier
                                    .padding(top = 15.dp, start = 100.dp)
                                    .weight(0.5f)
                                    .fillMaxWidth()
                                ,
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.SemiBold
                                ),
                            )


                        }
                        GlideImage(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(50.dp)),
                            imageModel= {"https://cdn-icons-png.freepik.com/512/8742/8742495.png"}
                        )
                        Column (
                            modifier = Modifier.padding(top = 9.dp, bottom = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = "Dra. Mónica Gonzales",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.SemiBold
                                ),
                            )
                            Text(
                                text = "monicagonzales@gmail.com",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Normal
                                ),
                            )
                        }

                        Card (
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Column(
                                modifier = Modifier.padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp)

                            ){
                                TextSemiBold(text = "Experiency: ")
                                TextNormal(text = "3 years")
                                TextSemiBold(text = "Bio: ")
                                TextNormal(text = "Dr. Monica gonzales is a higly experiences veterinarian with 11 years of dedicated practice.")
                            }
                        }
                        VetClinicCard()
                        CustomButton(text = "Edit Profile") {}
                        CustomButton(text = "Change Password") {}
                        CustomButton(text = "Logout") {}
                        HorizontalDivider(
                            thickness = 1.dp,
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun TextSemiBold(text: String) {
    val upetOrange = Color(0xFFFF8F86)
    Text(
        text = text,
        style = TextStyle(
            color = upetOrange,
            fontSize = 20.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold
        ),
    )
}
@Composable
fun TextNormal(text: String, color: Color = Color.Gray) {
    Text(
        text = text,
        style = TextStyle(
            color = color,
            fontSize = 15.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal
        ),
    )
}

@Composable
fun VetClinicCard(){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            GlideImage(imageModel = { "https://www.graphicwallet.com/wp-content/uploads/2021/01/PetCare-Logo_2.jpg" },
                modifier = Modifier
                    .padding(10.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    Text(
                        text = "PetPalace",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = Color.Black
                    )
                    TextNormal(text = "Veterinary Behavioral")

                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "Location",
                        tint = Color(0xFFFFB800)
                    )
                    TextNormal(text = "Av. Primavera 123")
                }


            }
        }
    }
}