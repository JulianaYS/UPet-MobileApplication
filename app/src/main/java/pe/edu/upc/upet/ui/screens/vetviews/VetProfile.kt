package pe.edu.upc.upet.ui.screens.vetviews

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import pe.edu.upc.upet.feature_auth.data.remote.UpdateUserRequest
import pe.edu.upc.upet.feature_auth.data.repository.AuthRepository
import pe.edu.upc.upet.feature_profile.data.remote.SubscriptionType
import pe.edu.upc.upet.feature_vetClinics.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic
import pe.edu.upc.upet.feature_vets.domain.Vet
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.ownerviews.ProfileImage
import pe.edu.upc.upet.ui.screens.ownerviews.VetClinicCard
import pe.edu.upc.upet.ui.screens.ownerviews.getRole
import pe.edu.upc.upet.ui.screens.ownerviews.getVet
import pe.edu.upc.upet.ui.screens.ownerviews.profile.InfoRowData
import pe.edu.upc.upet.ui.screens.ownerviews.profile.ProfileButtons
import pe.edu.upc.upet.ui.screens.ownerviews.profile.ProfileHeader
import pe.edu.upc.upet.ui.screens.ownerviews.profile.UserInfo
import pe.edu.upc.upet.ui.shared.ChangeProfileImageDialog
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.ImageEdit
import pe.edu.upc.upet.ui.shared.SuccessDialog
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.shared.uploadImage
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary
import pe.edu.upc.upet.ui.theme.poppinsFamily
import pe.edu.upc.upet.utils.TokenManager

@Composable
fun VetProfile(navController: NavHostController) {
    val vet = getVet() ?: return
    val userEmail = TokenManager.getEmail()?: return

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
            ProfileHeader(vet.id,vet.imageUrl)
            ProfileContent(navController, vet, userEmail)
            ProfileButtons(
                editProfile = { navController.navigate(Routes.VetEditProfile.route) },
                logout = {
                    TokenManager.clearToken()
                    navController.navigate(Routes.SignIn.route)
                }
            )
        }
    }
}


@Composable
fun ProfileContent(navController: NavHostController, vet: Vet, userEmail: String) {
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
