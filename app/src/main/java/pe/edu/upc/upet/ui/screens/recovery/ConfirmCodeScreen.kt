package pe.edu.upc.upet.ui.screens.recovery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.InputDigit

@Composable
fun ConfirmCodeScreen(navController: NavController) {
    Scaffold {paddingValues ->

        val digitOne = remember { mutableStateOf("") }
        val digitTwo = remember { mutableStateOf("") }
        val digitThree = remember { mutableStateOf("") }
        val digitFour = remember { mutableStateOf("") }
        val digitFive = remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(14.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp) // Applies spacing between all elements
        ) {
            CustomReturnButton(navController = navController)
            Text(
                text = "Check your email",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
            )
            Text(
                text = "We sent a reset link to you uPet@email.com. Enter the 5 digit code" +
                        "mentioned in the email",
                fontSize = 14.sp,
            )

            Row( // No need for additional modifiers here
                modifier = Modifier.padding(10.dp) // Padding applied to the entire Row
            ) {
                InputDigit(digitOne)
                InputDigit(digitTwo)
                InputDigit(digitThree)
                InputDigit(digitFour)
                InputDigit(digitFive)
            }

            CustomButton(text = "Verify Code", onClick = {
                navController.navigate(Routes.NewPassword)
            })
        }

    }
}
