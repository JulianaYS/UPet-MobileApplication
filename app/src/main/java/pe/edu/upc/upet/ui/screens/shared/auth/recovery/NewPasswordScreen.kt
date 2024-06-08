package pe.edu.upc.upet.ui.screens.shared.auth.recovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.PasswordTextField

@Composable
fun NewPasswordScreen( navController: NavController) {
    Scaffold(modifier = Modifier.padding(16.dp)) {paddingValues ->

        val password = remember { mutableStateOf("") }
        val confirmPassword = remember { mutableStateOf("") }

        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(10.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ){
            CustomReturnButton(navController =  navController)
            Text(
                text = "Set a new Password",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
            )
            Text(
                text = "Create a new password. Ensure it differs from previous passwords for security",
                fontSize = 14.sp,
            )
            Text(
                text = "Password",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
            PasswordTextField(input = password, placeholder = "Password")
            Text(
                text = "Repeat Password",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
            PasswordTextField(input = confirmPassword, placeholder = "Confirm Password")
            CustomButton(text = "Reset Password", onClick = {
                navController.navigate(Routes.SignIn.route)
            })
        }
    }
}