package pe.edu.upc.upet.ui.screens.ownerviews.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import pe.edu.upc.upet.feature_profile.domain.PetOwner
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.ownerviews.getOwner
import pe.edu.upc.upet.ui.screens.ownerviews.getRole
import pe.edu.upc.upet.ui.shared.ImageEdit
import pe.edu.upc.upet.ui.shared.SuccessDialog
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.shared.uploadImage
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.utils.TokenManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerProfile(navController: NavHostController) {

    val newImageUri = remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val petOwner = getOwner() ?: return

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        newImageUri.value = uri
    }

    Scaffold(
        topBar = {
            TopBar(navController = navController, title = "My Profile")
        },
        modifier = Modifier.padding(16.dp)
    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageEdit(
                imageUrl = petOwner.imageUrl,
                newImageUri = newImageUri.value,
                onImageClick = { imageLauncher.launch("image/*") }
            )

            Spacer(modifier = Modifier.height(16.dp))


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



            if (showDialog) {
                SuccessDialog(
                    onDismissRequest = { showDialog = false },
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
}




@Composable
fun ProfileButtons(navController: NavHostController, petOwner: PetOwner? = null) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val subscriptionRoute = if (petOwner?.subscriptionType == SubscriptionType.BASIC) {
            Routes.SubscriptionBasic.route
        } else {
            Routes.SubscriptionAdvanced.route
        }

        ActionButton(
            onClick = { navController.navigate(subscriptionRoute) },
            text = "Manage Subscription",
            icon = Icons.Default.Star,
            color = Pink,
        )

        ActionButton(
            onClick = { navController.navigate(Routes.OwnerEditProfile.route) },
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
