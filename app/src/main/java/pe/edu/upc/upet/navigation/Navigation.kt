package pe.edu.upc.upet.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.upet.ui.screens.auth.aditionalInformation.PostRegisterScreen
import pe.edu.upc.upet.ui.screens.auth.signin.SignInScreen
import pe.edu.upc.upet.ui.screens.auth.signup.SignUpScreen
import pe.edu.upc.upet.ui.screens.home.Home
import pe.edu.upc.upet.ui.screens.pets.PetList
import pe.edu.upc.upet.ui.screens.pets.PetProfile
import pe.edu.upc.upet.ui.screens.pets.RegisterPet
import pe.edu.upc.upet.ui.screens.recovery.ConfirmCodeScreen
import pe.edu.upc.upet.ui.screens.recovery.NewPasswordScreen
import pe.edu.upc.upet.ui.screens.recovery.SendEmailScreen
import pe.edu.upc.upet.ui.screens.vets.VetList
import pe.edu.upc.upet.ui.shared.BottomBar


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {

    val navController = rememberNavController()
    val backgroundColor = Color(0xFF0B1C3F)
    val shouldShowBottomBar = remember { mutableStateOf(true) }


    Scaffold(
        modifier = Modifier.background(backgroundColor),
        bottomBar = { BottomBar(navController, shouldShowBottomBar) }
    ) { paddingValues ->
        NavHost(navController, startDestination = Routes.UserLogin, modifier = Modifier.padding(paddingValues   )) {
            composable(Routes.Home) {
                shouldShowBottomBar.value = true
                Home(navController)
            }
            composable(Routes.PetList) {
                shouldShowBottomBar.value = true
                PetList(navController)
            }
            composable("PetProfile/{petId}") { backStackEntry ->
                shouldShowBottomBar.value = true
                val petId = backStackEntry.arguments?.getString("petId")?.toInt()
                PetProfile(petId, navController)
            }
            composable(Routes.registerPet) {
                shouldShowBottomBar.value = true
                RegisterPet(navController)
            }
            composable(Routes.VetList) {
                shouldShowBottomBar.value = true
                VetList(navController)
            }
            composable(Routes.UserRegister) {
                shouldShowBottomBar.value = false
                SignUpScreen() { destination ->
                    navController.navigate(destination)
                }
            }
            composable(Routes.UserLogin) {
                shouldShowBottomBar.value = false
                SignInScreen { destination ->
                    navController.navigate(destination)
                }
            }
            composable(Routes.PasswordRecovery) {
                shouldShowBottomBar.value = false
                SendEmailScreen(navController)
            }
            composable(Routes.ConfirmCode) {
                shouldShowBottomBar.value = false
                ConfirmCodeScreen(navController)
            }
            composable(Routes.NewPassword) {
                shouldShowBottomBar.value = false
                NewPasswordScreen(navController)
            }
            composable(Routes.PostRegister){
                shouldShowBottomBar.value = false
                PostRegisterScreen{
                    destination->
                    navController.navigate(destination)
                }
            }
        }
    }

}