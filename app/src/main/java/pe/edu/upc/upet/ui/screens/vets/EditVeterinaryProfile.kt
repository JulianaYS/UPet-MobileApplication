package pe.edu.upc.upet.ui.screens.vets

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.edu.upc.upet.feature_vetClinics.data.remote.VetRequest
import pe.edu.upc.upet.feature_vets.data.remote.VetResponse
import pe.edu.upc.upet.feature_vets.data.repository.VetRepository
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.CustomTextField
import pe.edu.upc.upet.ui.shared.ImageEdit
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.utils.TokenManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditVeterinaryProfile(vetId:Int?, navController: NavController) {

    val vet = remember {mutableStateOf<VetResponse?>(null) }
    val newImageUri = remember { mutableStateOf<Uri?>(null) }
    val userId = TokenManager.getUserIdAndRoleFromToken()?.first ?: 0

    LaunchedEffect(vetId) {
        VetRepository().getVetsByUserId(userId, callback = {

        })
    }

    val vetValue = vet.value ?: VetResponse(0,"",0,"",0)

    //var name by remember { mutableStateOf(vetValue.name) }
    val showSuccessDialog = remember { mutableStateOf(false) }

//    LaunchedEffect(vetValue) {
//        name = vetValue.name
//    }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        newImageUri.value = uri
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
    ) {
        paddingValues ->
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
                    ImageEdit(imageUrl = vetValue.image_url, newImageUri = newImageUri.value, onImageClick = { imageLauncher.launch("image/*") })

                    Spacer(modifier = Modifier.height(16.dp))

//                    CustomTextField(
//                        value = name,
//                        onValueChange = { name = it },
//                        label = "Name",
//                        leadingIcon = Icons.Default.TagFaces,
//                        modifier = Modifier.fillMaxWidth()
//                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { },
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
