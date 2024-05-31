package pe.edu.upc.upet.ui.screens.appointment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FactCheck
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.vets.TextNormal
import pe.edu.upc.upet.ui.shared.IconAndTextHeader
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun AppointmentList(navController: NavController) {

    Scaffold { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {

            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .padding(top = 10.dp, start = BorderPadding, end = BorderPadding),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconAndTextHeader(onBackClick = { navController.navigate(Routes.BookAppointmentScreen) }, text = "My appointments")
                        AppointmentCard("Dr. Anna Johanson", navController)
                        AppointmentCard("Dr. Anna Johanson", navController)
                        AppointmentCard("Dr. Anna Johanson", navController)
                        AppointmentCard("Dr. Anna Johanson", navController)
                        AppointmentCard("Dr. Anna Johanson", navController)
                    }
                }
            }
        }

    }


}

@Composable
fun AppointmentCardInfo(name: String){
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        ImageCircle(imageUrl = "https://www.graphicwallet.com/wp-content/uploads/2021/01/PetCare-Logo_2.jpg")

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
                    text = name,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.Black
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextNormal(text = "Veterinary")
                    TextNormal(text = " | ")
                    Box(
                        modifier = Modifier
                            .background(Color(0x37FFD700), RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Upcoming",
                            color = Color(0xFFE59500),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextNormal(text = "Aug 17, 2023")
                TextNormal(text = " | ")
                TextNormal(text = "11:00 AM")
            }
        }
    }
}



@Composable
fun AppointmentCard(name:String, navController: NavController){

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Routes.AppointmentDetail) },
    ) {
        Column {
            AppointmentCardInfo(name = name)
            DividerAndButtons(onCancelBookingClick = { })
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}



@Composable
fun DividerAndButtons(onCancelBookingClick :()->Unit){
    HorizontalDivider(
        thickness = 1.dp, color = Color(0xFFFF6262), modifier = Modifier.padding(horizontal = 16.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NewCustomButton(text = "Cancel Booking",modifier = Modifier.weight(1f), onClick = onCancelBookingClick)
        NewCustomButton(text = "Reschedule",modifier = Modifier.weight(1f), color = Color.White, onClick = { }, color2 = Blue1)
        
    }
}


@Composable
fun NewCustomButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFF6262),
    color2: Color = Color.White
) {
    var isPressed by remember { mutableStateOf(false) }

    Button(
        onClick = { isPressed=!isPressed },
        modifier = modifier
            .padding(horizontal = 4.dp)
            .background(
                if (isPressed) color else color2,
                shape = RoundedCornerShape(10.dp)
            ),
        border = if(color2 == Color.White) {if(isPressed) null else BorderStroke(1.dp, color)} else {if(isPressed) BorderStroke(1.dp, color2) else null},
        shape = RoundedCornerShape(10.dp),
        colors = ButtonColors(
            containerColor = if (isPressed) color else color2,
            contentColor = if (isPressed) color2 else color,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        )
    ) {
        Text(
            text = text,
            color = if (isPressed) color2 else color
        )
    }
}


@Composable
fun ImageCircle(imageUrl: String){
    GlideImage(imageModel = { imageUrl },
        modifier = Modifier
            .padding(10.dp)
            .size(100.dp)
            .clip(RoundedCornerShape(50))
    )
}


@Composable
fun AppointmentConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = null,
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Icon(
                    Icons.AutoMirrored.Filled.FactCheck,
                    "Back",
                    modifier =  Modifier.size(64.dp),
                    tint = Color(0xFF4CAF50)
                )
                Text(
                    text = "Your Appointment Has Been Confirmed",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF4CAF50)
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Your appointment with Dr. Anna Thorn on Wednesday, August 17, 2023 at 11:00 AM",
                    style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                modifier = Modifier.background(Color(0xFF4CAF50))

            ) {
                Text(text = "View Appointments", color = Color.White)
            }
        },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .background(Color.White)
    )
}

