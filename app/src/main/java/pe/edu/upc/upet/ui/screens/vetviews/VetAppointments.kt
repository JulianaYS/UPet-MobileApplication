package pe.edu.upc.upet.ui.screens.vetviews

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.edu.upc.upet.feature_appointment.data.repository.AppointmentRepository
import pe.edu.upc.upet.feature_appointment.domain.Appointment
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.feature_vets.data.repository.VetRepository
import pe.edu.upc.upet.feature_vets.domain.Vet
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.ownerviews.appointment.AppointmentCardDetails
import pe.edu.upc.upet.ui.screens.ownerviews.appointment.AppointmentFilterButtons
import pe.edu.upc.upet.ui.screens.ownerviews.appointment.ImageCircle
import pe.edu.upc.upet.ui.screens.ownerviews.getVet
import pe.edu.upc.upet.ui.shared.TopBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VetAppointments(navController: NavController) {
    var upcomingAppointments by remember { mutableStateOf(listOf<Appointment>()) }
    var pastAppointments by remember { mutableStateOf(listOf<Appointment>()) }
    var showUpcoming by remember { mutableStateOf(true) } // This is already set to true

    val vet = getVet() ?: return

    LaunchedEffect(vet.id) {
        AppointmentRepository().getUpcomingAppointmentsByVeterinarianId(1) { upcomingVetAppointments ->
            Log.d("UpcomingVetAppointments", upcomingVetAppointments.toString())
            upcomingAppointments = upcomingVetAppointments
        }
        AppointmentRepository().getPastAppointmentsByVeterinarianId(1) { pastVetAppointments ->
            Log.d("PastVetAppointments", pastVetAppointments.toString())

            pastAppointments = pastVetAppointments
        }
    }

    val appointments = if (showUpcoming) upcomingAppointments else pastAppointments

    Scaffold(
        topBar = { TopBar(navController = navController, title = "My Appointments") },
        modifier = Modifier.padding(16.dp)
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            AppointmentFilterButtons(
                onShowUpcomingChange = { upcoming ->
                    showUpcoming = upcoming
                }
            )
            AppointmentListContentVet(appointments, navController)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentListContentVet(
    appointments: List<Appointment>,
    navController: NavController
) {
    LazyColumn {
        items(appointments.size) { index ->
            AppointmentCardVet(appointments[index], navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentCardVet(appointment: Appointment, navController: NavController) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Routes.AppointmentDetail.createRoute(appointment.id)) },
    ) {
        AppointmentCardInfoVet(appointment)
        //DividerAndButtons()
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentCardInfoVet(appointment: Appointment) {
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

    vet?: return
    pet?: return

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(6.dp)
    ) {
        ImageCircle(imageUrl = pet!!.image_url)
        AppointmentCardDetails(pet!!.name, vet!!.name, appointment.status, appointment.startTime, appointment.endTime, appointment.day)
    }
}