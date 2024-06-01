package pe.edu.upc.upet.ui.screens.petOwner

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import pe.edu.upc.upet.feature_auth.data.remote.UpdateUserRequest
import pe.edu.upc.upet.feature_auth.data.repository.AuthRepository
import pe.edu.upc.upet.feature_profile.data.remote.SubscriptionType
import pe.edu.upc.upet.feature_profile.data.repository.PetOwnerRepository
import pe.edu.upc.upet.feature_profile.domain.PetOwner
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.pets.SuccessDialog
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.ImageEdit
import pe.edu.upc.upet.ui.shared.uploadImage
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.utils.TokenManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetOwnerProfile(navController: NavHostController) {
    val tokenData = remember { TokenManager.getUserIdAndRoleFromToken() }
    var newImageUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    if (tokenData == null) {
        navController.navigate(Routes.UserLogin)
        return
    }

    val (id, _, _) = tokenData
    var petOwner by remember { mutableStateOf<PetOwner?>(null) }
    val petOwnerRepository = remember { PetOwnerRepository() }

    LaunchedEffect(id) {
        petOwnerRepository.getPetOwnerById(id) {
            petOwner = it
        }
    }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        newImageUri = uri
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Blue1,
                    titleContentColor = Color.White
                ),
                title = { Text("My Profile") },
                navigationIcon = { CustomReturnButton(navController) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                ProfileContent(
                    petOwner = petOwner,
                    newImageUri = newImageUri,
                    showDialog = showDialog,
                    onImageClick = { imageLauncher.launch("image/*") },
                    onSaveImage = { uri ->
                        uploadImage(uri) { url, error ->
                            if (error != null) {
                                Log.e("Profile", "Failed to upload image", error)
                            } else {
                                val newImageUrl = url ?: ""
                                AuthRepository().updateUser(
                                    UpdateUserRequest(
                                        image_url = newImageUrl,
                                        role = tokenData.second
                                    )
                                ) { success ->
                                    if (success) {
                                        Log.d("Profile", "Image updated successfully")
                                        showDialog = true
                                        newImageUri = null
                                        petOwner?.imageUrl = newImageUrl
                                    } else {
                                        Log.e("Profile", "Failed to update image")
                                    }
                                }
                            }
                        }
                    },
                    onDialogDismiss = { showDialog = false },
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun ProfileContent(
    petOwner: PetOwner?,
    newImageUri: Uri?,
    showDialog: Boolean,
    onImageClick: () -> Unit,
    onSaveImage: (Uri) -> Unit,
    onDialogDismiss: () -> Unit,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageEdit(
            imageUrl = petOwner?.imageUrl
                ?: "https://cdn-icons-png.freepik.com/512/8742/8742495.png",
            newImageUri = newImageUri,
            onImageClick = onImageClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        newImageUri?.let {
            ActionButton(
                text = "Save Image",
                icon = Icons.Default.Image,
                color = Pink,
                onClick = { onSaveImage(it) }
            )
        }

        if (showDialog) {
            SuccessDialog(
                onDismissRequest = onDialogDismiss,
                titleText = "Image Updated",
                messageText = "Your profile image has been updated successfully.",
                buttonText = "OK"
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            PetOwnerInfo(petOwner = petOwner)
        }

        ProfileButtons(navController = navController, petOwner = petOwner)
    }
}

@Composable
fun ProfileButtons(navController: NavHostController, petOwner: PetOwner? = null) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val subscriptionRoute = if (petOwner?.subscriptionType == SubscriptionType.BASIC) {
            Routes.SubscriptionBasicScreen
        } else {
            Routes.SubscriptionAdvancedScreen
        }

        ActionButton(
            onClick = { navController.navigate(subscriptionRoute) },
            text = "Manage Subscription",
            icon = Icons.Default.Star,
            color = Pink,
        )

        ActionButton(
            onClick = { navController.navigate(Routes.PetOwnerEditProfile) },
            text = "Edit Profile",
            icon = Icons.Default.Edit,
            color = Pink
        )

        ActionButton(
            onClick = {
                AuthRepository().logOut()
                navController.navigate(Routes.UserLogin)
            },
            text = "Logout",
            icon = Icons.AutoMirrored.Filled.Logout,
            color = Pink
        )
    }
}

@Composable
fun ActionButton(onClick: () -> Unit, text: String, icon: ImageVector, color: Color) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Icon(imageVector = icon, contentDescription = "$text Icon")
        Text(text, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun PetOwnerInfo(petOwner: PetOwner?) {
    val userEmail = remember { TokenManager.getEmail() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = petOwner?.name ?: "",
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = userEmail ?: "",
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 16.sp
        )
        InfoRow(icon = Icons.Default.Home, label = "Location", value = petOwner?.location)
        InfoRow(icon = Icons.Default.Person4, label = "Phone", value = petOwner?.numberPhone)
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$label Icon",
            tint = Color(0xFF3F51B5)
        )
        Text(
            text = "$label: ${value ?: ""
            }",
            color = Color.Black,
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 18.sp
        )
    }
}
