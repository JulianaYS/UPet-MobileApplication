package pe.edu.upc.upet.ui.screens.petOwner

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_profile.domain.PetOwner


@Composable
fun PetOwnerProfile(navController: NavHostController) {

    val UpetOrange =Color(0xFFFF8F86)
    val petOwner = remember {
        mutableSetOf((PetOwner("FREE")))
    }
//0A1B3E
    Scaffold {
            paddingValues ->
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0B1C3F)),
                horizontalArrangement = Arrangement.Center // Center elements horizontally
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(0.1f)
                        .padding(top = 8.dp, start = 16.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.White)
                ) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Back", modifier = Modifier.fillMaxSize(1f))


                }
                Text(
                    text = "My Profile",
                    modifier = Modifier
                        .padding(top = 11.dp, start = 66.dp)
                        .weight(0.5f) // Give Text some weight for centering
                        .fillMaxWidth() // Stretch text across remaining width
                    ,
                    color = Color.White,
                    fontSize =30.sp
                )

            }
            Card (modifier = Modifier

                .background(Color(0xFF0B1C3F))
                .height(height = 250.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)

                ){
                ProfileImage(url = "https://media-cdn.tripadvisor.com/media/photo-s/12/0b/28/0e/para-compartir.jpg")
            }
            Box(
                modifier = Modifier

                    .background(Color(0xFF0A1B3E))
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
            ){
                Column {
                    //INFORMACION Y CONTENIDO DEL PROFILE

                        Column (modifier = Modifier.padding(15.dp)){
                            Row (
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,

                                ){
                                Text(
                                    modifier=Modifier.background(Color.White),
                                    text = "Mónica Galindo",
                                    fontSize = 36.sp,
                                    color = Color.Black)
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(imageVector = Icons.Default.Star,contentDescription = "Estrellita")

                                }
                            }
                            Text(
                                modifier= Modifier
                                    .background(Color.White)
                                    .fillMaxWidth(),text = "Pet Owner")
                            Text(
                                modifier=Modifier.background(Color.White),text = "Ubicación: Jr Lima 104, Santiago de Surco, Lima")
                            Spacer(modifier = Modifier.padding(top =20.dp, bottom = 20.dp))
                            Card (modifier = Modifier
                                .fillMaxWidth()
                                .size(height = 70.dp, width = 200.dp)){
                                Row(
                                    modifier = Modifier
                                        .background(Color(0xFF0A1B3E))
                                        .fillMaxWidth()
                                        .fillMaxHeight(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Row (modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 20.dp),
                                        verticalAlignment = Alignment.CenterVertically,){
                                        Box(modifier = Modifier
                                            .background(Color(0xFFFA6559))
                                            .size(width = 40.dp, height = 40.dp) ){

                                            Icon(imageVector = Icons.Default.Pets, contentDescription = "Perritos",
                                                tint = Color.White, modifier = Modifier.padding(start= 7.dp,top = 7.dp))
                                        }
                                        Text(modifier= Modifier.padding(start =10.dp),text = "My Pets",color = Color.White)
                                    }
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Pets",
                                            tint = Color.White)

                                    }
                                }
                            }
                            Spacer(modifier = Modifier.padding(top = 10.dp))
                            Card (modifier = Modifier
                                .fillMaxWidth()
                                .size(height = 70.dp, width = 200.dp)){
                                Row(
                                    modifier = Modifier
                                        .background(Color(0xFF0A1B3E))
                                        .fillMaxWidth()
                                        .fillMaxHeight(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Row (modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 20.dp),
                                        verticalAlignment = Alignment.CenterVertically,){
                                        Box(modifier = Modifier
                                            .background(Color(0xFFFA6559))
                                            .size(width = 40.dp, height = 40.dp) ){

                                            Icon(imageVector = Icons.Default.AttachMoney, contentDescription = "Perritos",
                                                tint = Color.White, modifier = Modifier.padding(start= 7.dp,top = 7.dp))
                                        }
                                        Text(modifier= Modifier.padding(start =10.dp),text = "Subscription",color = Color.White)
                                    }
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Pets",
                                            tint = Color.White)

                                    }
                                }
                            }
                            Spacer(modifier = Modifier.padding(top = 30.dp))
                            Button(onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = UpetOrange,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()

                            ) {
                                Text(text = "Cerrar Sesión")
                            }
                        }


                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Box(modifier = Modifier
                        .clip(shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                        .shadow(
                            shape = RoundedCornerShape(size = 0.5.dp),
                            elevation = 0.5.dp,
                        )
                        .fillMaxWidth()
                        .size(height = 300.dp, width = 2000.dp)){
                        Row(modifier = Modifier.padding(5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            ) {
                            Spacer(modifier = Modifier.padding(start = 15.dp))
                                Column {
                                    Card (

                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ), // Center content horizontally
                                    ){
                                        Icon(imageVector =Icons.Default.Home , contentDescription = "casita", modifier = Modifier.padding(start = 12.dp), tint = Color.Gray)
                                        Text(text = "Home", modifier = Modifier.background(Color.White),color = Color.Gray)
                                    }

                                }
                            Spacer(modifier = Modifier.padding(start = 35.dp))

                            Column {
                                Card (

                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ), // Center content horizontally
                                ){
                                    Icon(imageVector =Icons.Default.CalendarMonth , contentDescription = "casita", modifier = Modifier.padding(start = 40.dp), tint = Color.Gray)
                                    Text(text = "Appointment", modifier = Modifier.background(Color.White),color = Color.Gray)
                                }

                            }
                            Spacer(modifier = Modifier.padding(start = 35.dp))

                            Column {
                                Card (

                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ), // Center content horizontally
                                ){
                                    Icon(imageVector =Icons.Default.Person4 , contentDescription = "casita", modifier = Modifier.padding(start = 4.dp), tint = Color.Gray)
                                    Text(text = "Vets", modifier = Modifier.background(Color.White),color = Color.Gray)
                                }

                            }
                            Spacer(modifier = Modifier.padding(start = 35.dp))

                            Column {
                                Card (

                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ), // Center content horizontally
                                ){
                                    Icon(imageVector =Icons.Default.Person , contentDescription = "casita", modifier = Modifier.padding(start = 10.dp), tint = Color.Gray)
                                    Text(text = "Profile", modifier = Modifier.background(Color.White),color = Color.Gray)
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
fun ProfileImage(url: String ){
    GlideImage(modifier = Modifier.size(width = 350.dp, height = 170.dp),imageModel = { url })
}
