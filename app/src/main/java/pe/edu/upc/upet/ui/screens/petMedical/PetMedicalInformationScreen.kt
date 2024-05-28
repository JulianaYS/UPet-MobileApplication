package pe.edu.upc.upet.ui.screens.petMedical

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PetMedicalInformationScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppBar(navController = navController)
        PetImageBanner()
        PetInformationSection()
        MedicalHistorySection()
    }
}

@Composable
fun AppBar(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .background(Color(0xFF0A2540)),

        ) {
        Row{
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back", tint = Color.White)
            }
            Text(
                text = "Boby",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 125.dp, top = 12.dp)
            )

        }
    }
}

@Composable
fun PetImageBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFF0A2540)),
        contentAlignment = Alignment.Center
    ) {

        ProfileImage("https://mf.b37mrtl.ru/actualidad/public_images/2022.06/article/62aa0f81e9ff71530076e38b.jpg")
    }
}

@Composable
fun PetInformationSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)

    ) {
        Text(
            text = "General Information",
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {items(3){
            val heartIcon = Icons.Outlined.Favorite
            PetInformationCard("Specie", heartIcon,"Canis")
        }
        }
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
fun MedicalHistorySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Medical History",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(5) {
                val heartIcon = Icons.Outlined.MedicalServices
                MedicalHistoryCard("Diagnosis", "14/04/2024", "The animal presents high corporal temperature 1 hour ago. Also manifests stomach...",heartIcon)
            }
        }
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

@Composable
fun ProfileImage(url: String ){
    GlideImage(modifier = Modifier.size(width = 350.dp, height = 170.dp),imageModel = { url })
}

