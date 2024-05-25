package pe.edu.upc.upet.ui.screens.auth.aditionalInformation

import android.util.Log
import androidx.compose.runtime.Composable

import pe.edu.upc.upet.feature_auth.data.remote.UserType
import pe.edu.upc.upet.feature_auth.data.repository.AuthRepository

import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.auth.aditionalInformation.shared.PetOwnerPostRegister
import pe.edu.upc.upet.ui.screens.auth.aditionalInformation.shared.VeterinarianPostRegister
import pe.edu.upc.upet.utils.TokenManager

@Composable
fun PostRegisterScreen(
    navigateTo: (String) -> Unit ={}
){
    val (userId, userRole, isRegistered) = TokenManager.getUserIdAndRoleFromToken() ?: error("Error obteniendo el userId y userRole desde el token")
    Log.d("PostRegisterScreen", userId.toString())
    Log.d("PostRegisterScreen", userRole.toString())
    Log.d("PostRegisterScreen", isRegistered.toString())

    val role = UserType.valueOf(userRole)
    Log.d("PostRegisterScreen", "ROLE ${role}")
    if (isRegistered) {
        when (role) {
            UserType.Vet -> {
                Log.d("PostRegister", "VetHome")
                navigateTo(Routes.Home)
            }
            UserType.Owner -> {
                Log.d("PostRegister", "PetHome")
                navigateTo(Routes.Home)
            }

        }
    } else {
        when (role) {
            UserType.Vet-> {
                Log.d("PostRegister", "VeterinarianPostRegister")
                VeterinarianPostRegister(navigateTo, userId)
            }
            UserType.Owner -> {
                Log.d("PostRegister", "PetOwnerRegister")
                PetOwnerPostRegister(navigateTo, userId)
            }
        }
    }
}

