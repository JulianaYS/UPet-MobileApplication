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
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import pe.edu.upc.upet.feature_auth.data.remote.UpdateUserRequest
import pe.edu.upc.upet.feature_auth.data.repository.AuthRepository
import pe.edu.upc.upet.feature_vetClinics.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic
import pe.edu.upc.upet.feature_vets.domain.Vet
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.ownerviews.VetClinicCard
import pe.edu.upc.upet.ui.screens.ownerviews.getRole
import pe.edu.upc.upet.ui.screens.ownerviews.getVet
import pe.edu.upc.upet.ui.screens.ownerviews.profile.ActionButton
import pe.edu.upc.upet.ui.shared.ChangeProfileImageDialog
import pe.edu.upc.upet.ui.shared.ImageEdit
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.shared.uploadImage
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
        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .background(UpetBackGroundPrimary),
                    verticalArrangement = Arrangement.spacedBy(13.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    ProfileHeader(vet)
                    ProfileContent(navController, vet)
                    ProfileActions(navController, vet.id)
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(vet: Vet) {
    val newImageUri = remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        newImageUri.value = uri
    }

    ImageEdit(
        imageUrl = vet.imageUrl,
        newImageUri = newImageUri.value,
        onImageClick = { imageLauncher.launch("image/*") }
    )
    Column(
        modifier = Modifier.padding(top = 9.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
        Spacer(modifier = Modifier.height(10.dp))
        ActionButton(
            text = "Save Image",
            icon = Icons.Default.Image,
            color = Pink,
            onClick = {
                uploadImage(newImageUri.value!!) { url ->
                    Log.d("Profile", "Image URL: $url")
                    if(url != "") {
                        AuthRepository().updateUser(
                            UpdateUserRequest(
                                image_url = url,
                                role = getRole()
                            )
                        ) {
                            if (it) {
                                Log.d("Profile", "Image updated successfully")
                                showDialog = true
                                newImageUri.value = null
                            } else {
                                Log.e("Profile", "Failed to update image")
                            }
                        }
                    }
                }
            }
        )
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

    if(vet.experience == 0 && vet.description?.isEmpty() != false) {
        Text(
            text = "Add description and experience to get more clients",
            style = TextStyle(
                color = Color.White,
                fontSize = 15.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Normal
            ),)
        progress = 0f
    }else {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE0E0E0),
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
                vet.description?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Gray)
                    )
                }
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

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ActionButton(
            onClick = { navController.navigate(Routes.VetEditProfile.route) },
            text = "Edit Profile",
            icon = Icons.Default.Edit,
            color = Pink
        )
        ActionButton(
            onClick = {
                TokenManager.clearToken()
                navController.navigate(Routes.SignIn.route)
            },
            text = "Logout",
            icon = Icons.AutoMirrored.Filled.Logout,
            color = Pink
        )
    }
}