package pe.edu.upc.upet.navigation

import SubscriptionBasicScreen
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
import pe.edu.upc.upet.ui.screens.appointment.AppointmentDetail
import pe.edu.upc.upet.ui.screens.appointment.AppointmentList
import pe.edu.upc.upet.ui.screens.appointment.BookAppointmentScreen
import pe.edu.upc.upet.ui.screens.appointment.PetDetailsAppointmentScreen
import pe.edu.upc.upet.ui.screens.auth.aditionalInformation.PostRegisterScreen
import pe.edu.upc.upet.ui.screens.auth.signin.SignInScreen
import pe.edu.upc.upet.ui.screens.auth.signup.SignUpScreen
import pe.edu.upc.upet.ui.screens.home.Home
import pe.edu.upc.upet.ui.screens.petMedical.NewDiagnosisRegister
import pe.edu.upc.upet.ui.screens.petMedical.NewMedicalRegisterScreen
import pe.edu.upc.upet.ui.screens.petMedical.NewSurgeryRegister
import pe.edu.upc.upet.ui.screens.petMedical.NewTestResultRegister
import pe.edu.upc.upet.ui.screens.petMedical.NewVaccineRegister
import pe.edu.upc.upet.ui.screens.petMedical.PetMedicalInformationScreen
import pe.edu.upc.upet.ui.screens.petOwner.EditPetOwnerProfile
import pe.edu.upc.upet.ui.screens.petOwner.PetOwnerProfile
import pe.edu.upc.upet.ui.screens.pets.EditPetProfile
import pe.edu.upc.upet.ui.screens.pets.PetList
import pe.edu.upc.upet.ui.screens.pets.PetProfile
import pe.edu.upc.upet.ui.screens.pets.RegisterPet
import pe.edu.upc.upet.ui.screens.recovery.ConfirmCodeScreen
import pe.edu.upc.upet.ui.screens.recovery.NewPasswordScreen
import pe.edu.upc.upet.ui.screens.recovery.SendEmailScreen
import pe.edu.upc.upet.ui.screens.subscription.SubscriptionAdvancedScreen
import pe.edu.upc.upet.ui.screens.vets.VetList
import pe.edu.upc.upet.ui.screens.vets.VetProfile
import pe.edu.upc.upet.ui.screens.vets.VeterinaryClinicDetailsScreen
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
            composable(Routes.Profile) {
                shouldShowBottomBar.value = true
                PetOwnerProfile(navController)
            }
            composable(Routes.PetList) {
                shouldShowBottomBar.value = true
                PetList(navController)
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
            composable(Routes.VetProfile) {
                shouldShowBottomBar.value = true
                VetProfile()
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
            //Appointment
            composable(Routes.BookAppointmentScreen){
                shouldShowBottomBar.value = true
                BookAppointmentScreen(navController)
            }
            composable(Routes.PetDetailsAppointment){
                shouldShowBottomBar.value = true
                PetDetailsAppointmentScreen(navController)
            }
            composable(Routes.AppointmentList){
                shouldShowBottomBar.value=true
                AppointmentList(navController)
            }
            composable(Routes.AppointmentDetail){
                shouldShowBottomBar.value=true
                AppointmentDetail(navController)
            }
            //Subscription
            composable(Routes.SubscriptionBasicScreen){
                shouldShowBottomBar.value = true
                SubscriptionBasicScreen(navController)

            }
            composable(Routes.SubscriptionAdvancedScreen){
                shouldShowBottomBar.value = true
                SubscriptionAdvancedScreen(navController)

            }
            composable(Routes.PetOwnerEditProfile){
                shouldShowBottomBar.value = true
                EditPetOwnerProfile(navController)
            }
            composable("PetProfile/{petId}") { backStackEntry ->
                shouldShowBottomBar.value = true
                val petId = backStackEntry.arguments?.getString("petId")?.toInt()
                PetProfile(petId, navController)
            }
            composable(Routes.PetEdit+"/{petId}") {backStackEntry ->
                val petId = backStackEntry.arguments?.getString("petId")?.toInt()
                shouldShowBottomBar.value = true
                EditPetProfile( petId, navController)
            }
            composable(Routes.PetMedicalInformationScreen){
                shouldShowBottomBar.value = true
                PetMedicalInformationScreen(navController)
            }
            composable(Routes.NewMedicalRegisterScreen){
                shouldShowBottomBar.value = true
                NewMedicalRegisterScreen(navController)
            }
            composable(Routes.NewVaccineRegister){
                shouldShowBottomBar.value = true
                NewVaccineRegister(navController)
            }
            composable(Routes.NewSurgeryRegister){
                shouldShowBottomBar.value = true
                NewSurgeryRegister(navController)
            }
            composable(Routes.NewTestResultRegister){
                shouldShowBottomBar.value = true
                NewTestResultRegister(navController)
            }
            composable(Routes.NewDiagnosisRegister){
                shouldShowBottomBar.value = true
                NewDiagnosisRegister(navController)
            }
            composable("VetDetails/{vetClinicId}") { backStackEntry ->
                val vetClinicId = backStackEntry.arguments?.getString("vetClinicId")?.toInt()
                shouldShowBottomBar.value = true
                vetClinicId?.let { id ->
                    VeterinaryClinicDetailsScreen(navController, id)
                }
            }

        }
    }

}