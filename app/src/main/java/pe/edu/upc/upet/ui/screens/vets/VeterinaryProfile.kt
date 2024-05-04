package pe.edu.upc.upet.ui.screens.vets

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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person4
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.ui.theme.UpetOrange1


@Composable
fun VeterinaryProfile(){

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
                        .padding(top = 15.dp, start = 30.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                        .size(width = 30.dp, height = 30.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Back", modifier = Modifier.fillMaxSize(1f))


                }
                Text(
                    text = "Veterinary",
                    modifier = Modifier
                        .padding(top = 11.dp, start = 66.dp)
                        .weight(0.5f) // Give Text some weight for centering
                        .fillMaxWidth() // Stretch text across remaining width
                    ,
                    color = Color.White,
                    fontSize =25.sp
                )

            }
            Card (modifier = Modifier

                .background(Color(0xFF0B1C3F))
                .height(height = 170.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)

            ){
                ProfileImage(url = "https://media-cdn.tripadvisor.com/media/photo-s/12/0b/28/0e/para-compartir.jpg",150,350)
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
                                modifier= Modifier.background(Color.White),
                                text = "DR. Anna Jhonason",
                                fontSize = 20.sp,
                                color = Color(0xFFFA6559))
                        }
                        Text(
                            modifier= Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                            ,text = "Veterinary Behavioral",
                            color = Color.Gray
                            )
                        Spacer(modifier = Modifier.padding(top =10.dp, bottom = 10.dp))
                        Row(horizontalArrangement = Arrangement.Center){

                            RowCards(top = "Experience", bottom = "11 years")
                            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                            RowCards(top = "Price", bottom = "$250")

                            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                            RowCards(top = "Experience", bottom = "2.5 Km")
                        }
                        Spacer(modifier = Modifier.padding(top = 20.dp))
                        Text(text = "About")
                        Text(text = "Dr. Anna Nois is a highly experienced" +
                                " veterinarian with 11 years of dedicated " +
                                "practice, showcastigan a pro..",
                            color = Color.Gray,
                            fontSize = 12.sp)
                        Text(text = "Working Time")
                        Text(text = "Monday - Friday",
                            color = Color.Gray,
                            fontSize = 12.sp)
                        Row {

                            Text(text = "Reviews", modifier = Modifier.weight(1f))
                            Text(text = "See all", color = Color(0xFFFA6559))
                        }
                        //AQUI TRABAJANDOOOOOOOOOOOOO
                        Card(colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )) {
                            Column (modifier = Modifier.padding(bottom = 10.dp)){
                                Row (modifier = Modifier.padding(horizontal = 7.dp,vertical = 7.dp),
                                    verticalAlignment = Alignment.CenterVertically){
                                    Box(modifier = Modifier.clip(shape = RoundedCornerShape(20.dp)) )
                                    {
                                        ProfileImage(url = "https://media-cdn.tripadvisor.com/media/photo-s/12/0b/28/0e/para-compartir.jpg", 42,40)
                                    }
                                    Text(text = "Jane Cooper", modifier = Modifier
                                        .padding(start = 5.dp)
                                        .weight(1f))
                                    Column {
                                        Row (modifier = Modifier.padding(start= 50.dp)){
                                            Icon(imageVector = Icons.Default.Star, contentDescription = "Estrella", tint = Color(0xFFFFB800))
                                            Text(text = "5")
                                        }
                                        Text(text = "2 min ago", color =Color.Gray)
                                    }

                                }
                                Text(text = "Ullamco tempor adpisicsing et voluptate duis sit asd esse aliquea esse ex",color = Color.Gray)
                            }
                        }

                        Button(onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = UpetOrange1,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Text(text = "See Location")
                        }
                        Button(onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = UpetOrange1,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Text(text = "Cerrar Sesión")
                        }
                    }


                    Box(modifier = Modifier
                        .clip(shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                        .shadow(
                            shape = RoundedCornerShape(size = 0.5.dp),
                            elevation = 0.5.dp,
                        )
                        .fillMaxWidth()
                        .size(height = 100.dp, width = 2000.dp)){
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
                                    Icon(imageVector = Icons.Default.Home , contentDescription = "Home", modifier = Modifier.padding(start = 12.dp), tint = Color.Gray)
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
                                    Icon(imageVector = Icons.Default.CalendarMonth , contentDescription = "Appointment", modifier = Modifier.padding(start = 40.dp), tint = Color.Gray)
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
                                    Icon(imageVector = Icons.Default.Person4 , contentDescription = "Vets", modifier = Modifier.padding(start = 4.dp), tint = Color.Gray)
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
                                    Icon(imageVector = Icons.Default.Person , contentDescription = "Profile", modifier = Modifier.padding(start = 10.dp), tint = Color.Gray)
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
fun ProfileImage(url: String,size: Int,width: Int ){
    GlideImage(modifier = Modifier.size(width = width.dp, height = size.dp),imageModel = { url })
}
@Composable
fun RowCards(top: String, bottom: String){
    Card(modifier = Modifier
        .shadow(elevation = 8.dp, shape = RoundedCornerShape(10.dp))
        .size(height = 60.dp, width = 120.dp)
        ,
        ){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(5.dp)
            ) {
            Text(text = top,modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold)
            Text(text = bottom,modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold)
        }
    }
}