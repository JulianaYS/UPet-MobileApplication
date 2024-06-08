package pe.edu.upc.upet.ui.screens.vetviews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import pe.edu.upc.upet.ui.screens.ownerviews.getRole
import pe.edu.upc.upet.ui.screens.ownerviews.getVet
import pe.edu.upc.upet.ui.shared.HeaderHome

@Composable
fun VetHome(navController: NavHostController) {
    val vet = getVet() ?: return

    Column(Modifier.padding(16.dp)) {

        HeaderHome(imageUrl = vet.imageUrl, name = vet.name, secondaryText = getRole())

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Today's Appointments",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        LazyColumn {
            items(dummyAppointments) { appointment ->
                AppointmentCard(appointment)
            }
        }

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
    }
}

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