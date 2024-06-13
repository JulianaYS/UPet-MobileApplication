package pe.edu.upc.upet.ui.screens.ownerviews.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pe.edu.upc.upet.feature_profile.data.remote.EditPetOwnerRequest
import pe.edu.upc.upet.feature_profile.domain.PetOwner
import pe.edu.upc.upet.feature_profile.data.repository.PetOwnerRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.CustomTextField
import pe.edu.upc.upet.ui.shared.SuccessDialog
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.utils.TokenManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerEditProfile(navController: NavHostController) {
    val tokenData = TokenManager.getUserIdAndRoleFromToken()

    if (tokenData == null) {
        navController.navigate(Routes.SignIn.route)
        return
    }

    val (id, _, _) = tokenData

    var petOwner by remember { mutableStateOf<PetOwner?>(null) }
    var name by remember { mutableStateOf("") }
    var numberPhone by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    val showSuccessDialog = remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        PetOwnerRepository().getPetOwnerById(id) { it ->
            petOwner = it
            it?.let {
                name = it.name
                numberPhone = it.numberPhone
                location = it.location
            }
        }
    }

    if (showSuccessDialog.value) {
        SuccessDialog(onDismissRequest = {
            showSuccessDialog.value = false
            navController.navigateUp()
        }, titleText = "Profile Updated",
            messageText = "Your profile has been updated successfully.",
            buttonText = "OK")
    }

    Scaffold(
        topBar = {
            TopBar(navController = navController, title = "Edit Profile")
        },
        modifier = Modifier.padding(16.dp)
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

                    CustomTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Name",
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = numberPhone,
                        onValueChange = { numberPhone = it },
                        label = "Phone Number",
                        leadingIcon = Icons.Default.Call
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = "Location",
                        leadingIcon = Icons.Default.Place
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val updatedPetOwner = petOwner?.copy(
                                name = name,
                                numberPhone = numberPhone,
                                location = location
                            )
                            if (updatedPetOwner != null) {
                                PetOwnerRepository().updatePetOwner(
                                    id,
                                    EditPetOwnerRequest(
                                        name = updatedPetOwner.name,
                                        numberPhone = updatedPetOwner.numberPhone,
                                        location = updatedPetOwner.location,
                                    ),
                                ) {
                                    if (it) {
                                        showSuccessDialog.value = true
                                    }
                                }
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
