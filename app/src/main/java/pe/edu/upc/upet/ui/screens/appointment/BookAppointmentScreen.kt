package pe.edu.upc.upet.ui.screens.appointment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.poppinsFamily
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookAppointmentScreen(navController: NavController, clinicId: Int) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }

    Scaffold( topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Blue1,
                titleContentColor = Color.White,
            ),
            title = {
                Text("Book Appointment")
            },
            navigationIcon = {
                CustomReturnButton(navController = navController)
            }
        )
    }) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .padding(top = 10.dp, start = BorderPadding, end = BorderPadding),
                        verticalArrangement = Arrangement.Top
                    ) {
                        TextSubtitle("Select Date")

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0x31E0E0E0), RoundedCornerShape(15.dp))
                                .padding(8.dp)
                        ) {
                            Column {
                                MonthPicker(
                                    currentYearMonth = currentYearMonth,
                                    onYearMonthChange = { newYearMonth ->
                                        currentYearMonth = newYearMonth
                                    }
                                )

                                CalendarView(
                                    currentYearMonth = currentYearMonth,
                                    selectedDate = selectedDate,
                                    onDateSelected = { date ->
                                        selectedDate = date
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        TextSubtitle("Select Available Hour")

                        TimePickerView(
                            selectedTime = selectedTime,
                            onTimeSelected = { time ->
                                selectedTime = time
                            }
                        )
                        Spacer(modifier = Modifier.height(25.dp))

                        CustomButton(text = "Next", onClick = {
                            navController.navigate("${Routes.PetDetailsAppointment}/$clinicId/${selectedDate}/${selectedTime}")
                        })
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthPicker(currentYearMonth: YearMonth, onYearMonthChange: (YearMonth) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = {
            onYearMonthChange(currentYearMonth.minusMonths(1))
        }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBackIos, contentDescription = "Previous Month")
        }

        Text(
            text = currentYearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        IconButton(onClick = {
            onYearMonthChange(currentYearMonth.plusMonths(1))
        }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "Next Month")
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(currentYearMonth: YearMonth, selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val today = LocalDate.now()
    val daysInMonth = currentYearMonth.lengthOfMonth()
    val firstDayOfMonth = currentYearMonth.atDay(1).dayOfWeek.value % 7

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        for (week in 0 until 6) {
            Row {
                for (day in 0 until 7) {
                    val dayOfMonth = week * 7 + day - firstDayOfMonth + 1
                    if (dayOfMonth in 1..daysInMonth) {
                        val date = currentYearMonth.atDay(dayOfMonth)
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .border(
                                    width = 2.dp,
                                    color = if (date == today) Color.White else Color.Transparent,
                                    shape = CircleShape,
                                )
                                .background(
                                    if (selectedDate == date) Color.White else Color.Transparent,
                                    RoundedCornerShape(50)
                                )
                                .clickable {
                                    onDateSelected(date)
                                }
                        ) {
                            Text(
                                text = dayOfMonth.toString(),
                                color = when {
                                    selectedDate == date -> Color.Black
                                    else -> Color.White
                                },
                                modifier = Modifier.align(Alignment.Center),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePickerView(selectedTime: LocalTime?, onTimeSelected: (LocalTime) -> Unit) {
    val times = remember {
        (8..18).flatMap { hour ->
            listOf(
                LocalTime.of(hour, 0),
                LocalTime.of(hour, 30)
            )
        }
    }

    val rows = times.chunked(4)

    Column {
        rows.forEach { rowTimes ->
            Row(modifier = Modifier.fillMaxWidth()) {
                rowTimes.forEach { time ->
                    val isSelected = selectedTime == time
                    val backgroundColor = if (isSelected) Color(0xFFFF8F86) else Color.White
                    val textColor = if (isSelected) Color.White else Color.Gray
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(75.dp)
                            .padding(4.dp)
                            .background(backgroundColor, RoundedCornerShape(15.dp))
                            .clickable {
                                onTimeSelected(time)
                            }
                            .wrapContentSize(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = time.format(DateTimeFormatter.ofPattern("hh:mm")),
                            color = textColor,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = time.format(DateTimeFormatter.ofPattern("a")),
                            color = textColor,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold),

                        )
                    }
                }
                repeat(4 - rowTimes.size) {
                    Spacer(modifier = Modifier
                        .weight(1f)
                        .height(75.dp))
                }
            }
        }
    }
}

@Composable
fun TextSubtitle(text: String){
    Text(
        modifier = Modifier.padding(bottom = 15.dp),
        text = text,
        style = TextStyle(
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold
        ),
    )
}