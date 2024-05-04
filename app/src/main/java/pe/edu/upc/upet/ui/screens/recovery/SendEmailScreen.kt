package pe.edu.upc.upet.ui.screens.recovery

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import pe.edu.upc.upet.ui.shared.InputTextField

@Composable
fun SendEmailScreen(navController: NavController) {
    Scaffold {paddingValues ->

        val email = remember { mutableStateOf("") }

        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(10.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ){
            CustomReturnButton(navController = navController)
            Text(
                text = "Forgot Password",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
            )
            Text(
                text = "Please enter your email to reset the password",
                fontSize = 14.sp,
            )
            Text(
                text = "Your Email",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
            InputTextField(input = email, placeholder = "Enter your email")
            CustomButton(text = "Send Email", onClick = {
                navController.navigate(Routes.ConfirmCode)
            })
        }
    }
}
