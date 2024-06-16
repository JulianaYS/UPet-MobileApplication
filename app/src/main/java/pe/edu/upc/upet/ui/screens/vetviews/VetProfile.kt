package pe.edu.upc.upet.ui.screens.vetviews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pe.edu.upc.upet.feature_vetClinics.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic
import pe.edu.upc.upet.feature_vets.domain.Vet
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.ownerviews.VetClinicCard
import pe.edu.upc.upet.ui.screens.ownerviews.getVet
import pe.edu.upc.upet.ui.screens.ownerviews.profile.InfoRowData
import pe.edu.upc.upet.ui.screens.ownerviews.profile.ProfileButtons
import pe.edu.upc.upet.ui.screens.ownerviews.profile.ProfileHeader
import pe.edu.upc.upet.ui.screens.ownerviews.profile.UserInfo
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.utils.TokenManager

@Composable
fun VetProfile(navController: NavHostController) {
    val vet = getVet() ?: return
    val userEmail = TokenManager.getEmail() ?: return

    Scaffold(
        topBar = { TopBar(navController = navController, title = "My Profile") },
        modifier = Modifier.padding(16.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            ProfileHeader(vet.id, vet.imageUrl)
            ProfileContent(navController, vet, userEmail)

             ProfileButtons(
                editProfile = { navController.navigate(Routes.VetEditProfile.route) },
                logout = {
                    TokenManager.clearToken()
                    navController.navigate(Routes.SignIn.route)
                },
                 generatePassword = {
                        navController.navigate(Routes.GeneratePassword.createRoute(vet.clinicId))
                 }
            )
        }
    }
}


@Composable
fun ProfileContent(navController: NavHostController, vet: Vet,  userEmail: String) {
    val clinic = remember { mutableStateOf<VeterinaryClinic?>(null) }

    UserInfo(name = "Vet : " + vet.name, email = userEmail, infoRows =
    listOf(
        InfoRowData(Icons.Default.Person4, "Description ", vet.description),
        InfoRowData(Icons.Default.Home, "Experience ", vet.experience.toString()+" years"),
    )
    )

    LaunchedEffect(key1 = vet.clinicId) {
        VeterinaryClinicRepository().getVeterinaryClinicById(vet.clinicId) {
            clinic.value = it
        }
    }

    clinic.value?.let {
        VetClinicCard(navController, it)
    }
}
