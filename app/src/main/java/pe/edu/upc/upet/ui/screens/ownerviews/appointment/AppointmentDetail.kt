package pe.edu.upc.upet.ui.screens.ownerviews.appointment

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.edu.upc.upet.ui.shared.IconAndTextHeader
import pe.edu.upc.upet.ui.theme.BorderPadding


@Composable
fun AppointmentDetail(navController: NavController, appointmentId: Int) {
    Scaffold(
        modifier = Modifier.padding(16.dp)) { paddingValues ->
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
                        IconAndTextHeader(onBackClick = { navController.popBackStack() }, text = "Appointment Detail")
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Column {
                                AppointmentCardInfo(name = "Dra. Anna Johanson")
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        TextSubtitle("Schedule Appointment")

                        AppointmentScheduleInfo("Monday August 17, 2023", "11:00 AM")

                        Spacer(modifier = Modifier.height(20.dp))

                        TextSubtitle("Patient Information")

                        PatientInformation("Bobby","Hello, my dog has suddenly loss of appetite and lethargy.. View More" )

                    }
                }
            }
        }

    }

}


@Composable
fun AppointmentScheduleInfo(day:String , hour:String){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Date", tint = Color.White)
            Text(text = day, color= Color(0xFFFF6262))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(imageVector = Icons.Default.AccessTime, contentDescription = "Time", tint = Color.White)
            Text(text = hour, color= Color(0xFFFF6262))
        }
    }
}


@Composable
fun PatientInformation(namePet: String, problem:String) {
    val color = Color.White
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Pet: ", color = color)
            Text(text = namePet, color = color)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Problem: ", color = color)
            Text(text = problem, color = color)

        }
    }
}