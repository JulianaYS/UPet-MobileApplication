package pe.edu.upc.upet.ui.screens.vetviews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_auth.data.repository.AuthRepository
import pe.edu.upc.upet.feature_vetClinics.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic
import pe.edu.upc.upet.feature_vets.domain.Vet
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.ownerviews.VetClinicCard
import pe.edu.upc.upet.ui.screens.ownerviews.getVet
import pe.edu.upc.upet.ui.shared.ChangeProfileImageDialog
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary
import pe.edu.upc.upet.ui.theme.poppinsFamily
import pe.edu.upc.upet.utils.TokenManager

@Composable
fun VetProfile(navController: NavHostController) {

    val vet = getVet() ?: return
    Scaffold(
        topBar = {
            TopBar(navController = navController, title = "My Profile")
        },
        modifier = Modifier.padding(16.dp)
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(UpetBackGroundPrimary),
                verticalArrangement = Arrangement.spacedBy(13.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ProfileHeader(navController, vet)
                ProfileContent(navController, vet)
                ProfileActions(navController, vet.id)
            }
        }
    }
}

@Composable
fun ProfileHeader(navController: NavHostController, vet: Vet) {

    GlideImage(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(50.dp)),
        imageModel = { vet.imageUrl }
    )
    Column(
        modifier = Modifier.padding(top = 9.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = vet.name,
            style = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold
            ),
        )
        val email = TokenManager.getEmail()
        if (email != null) {
            Text(
                text = email,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal
                ),
            )
        }
    }
}

@Composable
fun ProgressBar(progress: Float) {
    Box(
        modifier = Modifier
            .height(20.dp)
            .width(300.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .background(Color.Blue, RoundedCornerShape(10.dp))
        )
    }
}
@Composable
fun ProfileContent(navController: NavHostController, vet: Vet) {
    val clinic = remember { mutableStateOf<VeterinaryClinic?>(null) }
    var progress = 5f

    if(vet.experience == 0 && vet.description.isEmpty()) {
        Text(
            text = "Add description and experience to get more clients",
            style = TextStyle(
                color = Color.White,
                fontSize = 15.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Normal
            ),)
        progress = 0f
    } else if(vet.experience == 0 || vet.description.isNotEmpty()) {
        progress = 0.5f
    } else if(vet.experience != 0 || vet.description.isEmpty()) {
        progress = 0.5f
    }else {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE0E0E0), // Light gray background
            ),
            shape = RoundedCornerShape(16.dp), // Rounded corners
            elevation = CardDefaults.cardElevation(8.dp), // Elevation
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Margin around the card
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ), // Padding inside the card
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Experience: ",
                    style = TextStyle(fontWeight = FontWeight.Bold, color = Pink)
                )
                if (vet.experience != 0) {
                    Text(
                        text = "${vet.experience} years",
                        style = TextStyle(color = Color.Gray)
                    )
                }
                Text(
                    text = "Bio: ",
                    style = TextStyle(fontWeight = FontWeight.Bold, color = Pink)
                )
                Text(
                    text = vet.description,
                    style = TextStyle(color = Color.Gray)
                )
            }
        }
    }
    ProgressBar(progress)

    LaunchedEffect(key1 = vet.clinicId) {
        VeterinaryClinicRepository().getVeterinaryClinicById(vet.clinicId) {
            clinic.value = it
        }
    }

    clinic.value?.let {
        VetClinicCard(navController, it)
    }
}


@Composable
fun ProfileActions(navController: NavHostController, vetId: Int) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        ChangeProfileImageDialog(navController, vetId.toString()) {
            showDialog = false
        }
    }

    CustomButton(text = "Edit Profile") {
        navController.navigate(Routes.VetEditProfile.route)
    }
    CustomButton(text = "Logout") {
        navController.navigate(Routes.SignIn.route)
        AuthRepository().logOut()
    }
    CustomButton(text = "Change Profile Image") {
        showDialog = true
    }

    HorizontalDivider(
        thickness = 1.dp,
    )
}