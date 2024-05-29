package pe.edu.upc.upet.ui.screens.petOwner

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_profile.domain.PetOwner
import pe.edu.upc.upet.feature_profile.data.repository.PetOwnerRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.CustomTextField
import pe.edu.upc.upet.ui.shared.ImageEdit
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.utils.TokenManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPetOwnerProfile(navController: NavHostController) {
    val tokenData = TokenManager.getUserIdAndRoleFromToken()

    if (tokenData == null) {
        navController.navigate(Routes.UserLogin)
        return
    }

    val (id, _, _) = tokenData

    var petOwner by remember { mutableStateOf<PetOwner?>(null) }
    var name by remember { mutableStateOf("") }
    var numberPhone by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var newImageUri by remember { mutableStateOf<Uri?>(null) }

    // Simula la carga del propietario de la mascota desde un repositorio.
    LaunchedEffect(id) {
        PetOwnerRepository().getPetOwnerById(id) { it ->
            petOwner = it
            it?.let {
                name = it.name
                numberPhone = it.numberPhone
                location = it.location
                imageUrl = it.imageUrl
            }
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue1,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text("Edit Profile")
                },
                navigationIcon = {
                    CustomReturnButton(navController = navController)
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ImageEdit(imageUrl = imageUrl, newImageUri = newImageUri, onImageClick = { imageLauncher.launch("image/*") })

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Name",
                        leadingIcon = Icons.Default.Person,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = numberPhone,
                        onValueChange = { numberPhone = it },
                        label = "Phone Number",
                        leadingIcon = Icons.Default.Call,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = "Location",
                        leadingIcon = Icons.Default.Place,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val updatedPetOwner = petOwner?.copy(
                                name = name,
                                numberPhone = numberPhone,
                                location = location,
                                imageUrl = newImageUri?.toString() ?: imageUrl
                            )
                            if (updatedPetOwner != null) {
                                // PetOwnerRepository().updatePetOwner(updatedPetOwner) {
                                //  navController.navigateUp()
                                // }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Pink),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Save Changes", color = Color.White)
                    }
                }
            }
        }
    }
}