package pe.edu.upc.upet.ui.screens.ownerviews.appointment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_appointment.data.repository.AppointmentRepository
import pe.edu.upc.upet.feature_appointment.domain.Appointment
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.feature_vets.data.repository.VetRepository
import pe.edu.upc.upet.feature_vets.domain.Vet
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.ownerviews.getOwner
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.poppinsFamily
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentList(navController: NavController) {
    var appointments by remember { mutableStateOf(listOf<Appointment>()) }
    var filteredAppointments by remember { mutableStateOf(listOf<Appointment>()) }
    var showUpcoming by remember { mutableStateOf(true) }

    val owner = getOwner() ?: return

    LaunchedEffect(owner.id) {
        PetRepository().getPetsByOwnerId(owner.id) { pets ->
            pets.forEach { pet ->
                AppointmentRepository().getAppointmentsByPetId(pet.id) { petAppointments ->
                    appointments = appointments + petAppointments
                    filteredAppointments = filterAppointments(appointments, showUpcoming)
                }
            }
        }
    }

    Scaffold(
        topBar = { TopBar(navController = navController, title = "My Appointments") },
        modifier = Modifier.padding(16.dp)
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            AppointmentFilterButtons(
                onShowUpcomingChange = { upcoming ->
                    showUpcoming = upcoming
                    filteredAppointments = filterAppointments(appointments, showUpcoming)
                }
            )
            AppointmentListContent(filteredAppointments, navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun filterAppointments(appointments: List<Appointment>, showUpcoming: Boolean): List<Appointment> {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    return appointments.filter { appointment ->
        val appointmentDate = LocalDate.parse(appointment.day, formatter)
        (appointmentDate.isAfter(today) || appointmentDate.isEqual(today)) == showUpcoming
    }
}

@Composable
fun AppointmentFilterButtons(
    onShowUpcomingChange: (Boolean) -> Unit
) {
    var isUpcomingSelected by remember { mutableStateOf(true) }

    Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(
            onClick = {
                onShowUpcomingChange(true)
                isUpcomingSelected = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isUpcomingSelected) Pink else Color.White,
                contentColor = if (isUpcomingSelected) Color.White else Pink,
            )
        ) {
            Text("Upcoming")
        }
        Button(
            onClick = {
                onShowUpcomingChange(false)
                isUpcomingSelected = false
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isUpcomingSelected) Color.White else Pink,
                contentColor = if (isUpcomingSelected) Pink else Color.White, )
        ) {
            Text("Past")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentListContent(
    appointments: List<Appointment>,
    navController: NavController
) {
    LazyColumn {
        items(appointments.size) { index ->
            AppointmentCard(appointments[index], navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentCard(appointment: Appointment, navController: NavController) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Routes.AppointmentDetail.createRoute(appointment.id)) },
    ) {
        AppointmentCardInfo(appointment, navController)
        //DividerAndButtons()
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentCardInfo(appointment: Appointment, navController: NavController) {
    var vet by remember { mutableStateOf<Vet?>(null) }
    var pet by remember { mutableStateOf<Pet?>(null) }

    LaunchedEffect(appointment.veterinarianId) {
        VetRepository().getVetById(appointment.veterinarianId) {
            vet = it
        }
    }

    LaunchedEffect(appointment.petId) {
        PetRepository().getPetById(appointment.petId) {
            pet = it
        }
    }


    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val appointmentDate = LocalDate.parse(appointment.day, formatter)
    val today = LocalDate.now()

    val statusText = if (appointmentDate.isAfter(today) || appointmentDate.isEqual(today)) {
        "Upcoming"
    } else {
        "Past"
    }

    vet?: return
    pet?: return

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(6.dp)
    ) {
        ImageCircle(imageUrl = vet!!.imageUrl)
        AppointmentCardDetails(vet!!.name, pet!!.name, statusText, appointment.startTime, appointment.endTime, appointment.day)
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentCardDetails(name: String, petName: String, statusText: String, startTime: String, endTime: String, day: String) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
                text = name,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.SemiBold
                )
            )

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
                modifier = Modifier
                    .background(Color(0x37FFb0bb), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Pet: $petName",
                    color = Pink,
                    style = TextStyle(fontSize = 16.sp)
                )
            }

            Box(
                modifier = Modifier
                    .background(Color(0x37FFD700), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = statusText,
                    color = Color(0xFFE59500),
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val formattedDay = LocalDate.parse(day, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .format(DateTimeFormatter.ofPattern("d MMMM", java.util.Locale("es", "ES")))
            val formattedTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm:ss"))
                .format(DateTimeFormatter.ofPattern("h:mm a"))
            Text(text = "$formattedDay | $formattedTime", color = Color.Gray)
        }
    }
}

@Composable
fun DividerAndButtons() {
    HorizontalDivider(thickness = 1.dp, color = Color(0xFFFF6262), modifier = Modifier.padding(horizontal = 16.dp))
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NewCustomButton(text = "Cancel Booking", modifier = Modifier.weight(1f))
        NewCustomButton(
            text = "Reschedule",
            modifier = Modifier.weight(1f),
            color = Color.White,
            color2 = Blue1
        )
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
        onClick = { isPressed = !isPressed },
        modifier = modifier
            .padding(horizontal = 4.dp)
            .background(
                if (isPressed) color else color2,
                shape = RoundedCornerShape(10.dp)
            ),
        border = if (color2 == Color.White) {
            if (isPressed) null else BorderStroke(1.dp, color)
        } else {
            if (isPressed) BorderStroke(1.dp, color2) else null
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
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
fun ImageCircle(imageUrl: String) {
    GlideImage(
        imageModel = { imageUrl },
        modifier = Modifier
            .padding(10.dp)
            .size(100.dp)
            .clip(RoundedCornerShape(50))
    )
}
