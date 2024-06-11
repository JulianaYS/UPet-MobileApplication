package pe.edu.upc.upet.ui.screens.vetviews

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import pe.edu.upc.upet.feature_appointment.data.repository.AppointmentRepository
import pe.edu.upc.upet.ui.screens.ownerviews.getRole
import pe.edu.upc.upet.ui.screens.ownerviews.getVet
import pe.edu.upc.upet.ui.shared.HeaderHome
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_appointment.domain.Appointment
import pe.edu.upc.upet.ui.screens.ownerviews.getOwner
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun VetHome(navController: NavHostController) {
    val vet = getVet() ?: return
    Log.d("VetHome", "VetHome")

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { UserSectionVet() }
        //item { AppointmentsSection(navController) }
        //item { PatientsSection(navController) }
    }



    /*Column(Modifier.padding(16.dp)) {

        HeaderHome(imageUrl = vet.imageUrl, name = vet.name, secondaryText = getRole())

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Today's Appointments",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
/*
        LazyColumn {
            items(dummyAppointments) { appointment ->
                AppointmentCard(appointment)
            }
        }*/

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Recent Patients",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        LazyColumn {
            items(dummyPatients) { patient ->
                PatientCard(patient)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }*/
}
/*
@Composable
fun AppointmentCard(appointment: Appointment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Appointment with ${appointment.petOwnerName}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Pet: ${appointment.petName}",
                fontSize = 14.sp
            )
            Text(
                text = "Time: ${appointment.time}",
                fontSize = 14.sp
            )
        }
    }
}*/

@Composable
fun UserSectionVet() {
    val vet = getVet() ?: return
    Log.d("UserSection", "Vet: $vet")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GlideImage(modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(50.dp)),
                imageModel = { vet.imageUrl },
            )
            Column(modifier = Modifier.padding(start = 20.dp)) {
                val greeting = "Hello, ${vet.name}"
                Text(
                    text = greeting,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                Text(
                    text = "Welcome!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications",
            tint = Color.White
        )
    }
}

@Composable
fun PatientCard(patient: Patient) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = patient.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Species: ${patient.species}",
                fontSize = 14.sp
            )
            Text(
                text = "Age: ${patient.age} years",
                fontSize = 14.sp
            )
        }
    }
}

val dummyAppointments = listOf(
    Appointment("John Doe", "Buddy", "10:00 AM"),
    Appointment("Jane Smith", "Whiskers", "11:00 AM")
)

val dummyPatients = listOf(
    Patient("Buddy", "Dog", 5),
    Patient("Whiskers", "Cat", 3)
)


data class Appointment(val petOwnerName: String, val petName: String, val time: String)
data class Patient(val name: String, val species: String, val age: Int)


/*
@Composable
fun AppointmentsSection(navController: NavController) {
    var appointments by remember { mutableStateOf(listOf<Appointment>()) }
    var filteredAppointments by remember { mutableStateOf(listOf<Appointment>()) }
    var showUpcoming by remember { mutableStateOf(true) }

    val vet = getVet() ?: return

    LaunchedEffect(vet.id) {
        AppointmentRepository().getAppointmentsByVeterinarianId(vet.id) { vetAppointments ->
            appointments = vetAppointments
            filteredAppointments = filterAppointments(appointments, showUpcoming).take(2)
        }
    }

    Column {
        Text(
            text = "Recommended Veterinary Clinics",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        Column {
            LazyColumn {
                items(filteredAppointments.size) { it ->
                    AppointmentCardVet(filteredAppointments[it], navController)
                }
            }
        }
    }
}


private fun filterAppointments(appointments: List<Appointment>, showUpcoming: Boolean): List<Appointment> {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    return appointments.filter { appointment ->
        val appointmentDate = LocalDate.parse(appointment.day, formatter)
        (appointmentDate.isAfter(today) || appointmentDate.isEqual(today)) == showUpcoming
    }.sortedByDescending { appointment ->
        LocalDate.parse(appointment.day, formatter)
    }
}
*/