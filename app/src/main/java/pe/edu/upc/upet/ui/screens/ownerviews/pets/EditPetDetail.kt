package pe.edu.upc.upet.ui.screens.ownerviews.pets

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.edu.upc.upet.feature_pet.data.remote.GenderEnum
import pe.edu.upc.upet.feature_pet.data.remote.PetRequest
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.CustomTextField
import pe.edu.upc.upet.ui.shared.ImageEdit
import pe.edu.upc.upet.ui.shared.SuccessDialog
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.utils.TokenManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPetDetail(navController: NavController, petId: Int) {
    var pet by remember { mutableStateOf<Pet?>(null) }

    val newImageUri = remember { mutableStateOf<Uri?>(null) }
    LaunchedEffect(petId) {
        PetRepository().getPetById(petId){
            pet = it
        }
    }

    val petValue = pet?: return

    // Separate mutable states for each field
    var name by remember { mutableStateOf(petValue.name) }
    var breed by remember { mutableStateOf(petValue.breed) }
    var species by remember { mutableStateOf(petValue.specie) }
    var weight by remember { mutableStateOf(petValue.weight.toString()) }
    var birthdate by remember { mutableStateOf(petValue.birthdate) }
    val showSuccessDialog = remember { mutableStateOf(false) }


    // Update fields when petValue changes
    LaunchedEffect(petValue) {
        name = petValue.name
        breed = petValue.breed
        species = petValue.specie
        weight = petValue.weight.toString()
        birthdate = petValue.birthdate
    }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        newImageUri.value = uri
    }

    Scaffold(
        topBar = {
            TopBar(navController = navController, title = "Edit Pet Details")
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
                    ImageEdit(imageUrl = petValue.image_url, newImageUri = newImageUri.value, onImageClick = { imageLauncher.launch("image/*") })

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Name",
                        leadingIcon = Icons.Default.TagFaces,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = breed,
                        onValueChange = { breed = it },
                        label = "Breed",
                        leadingIcon = Icons.Default.Pets,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = species,
                        onValueChange = { species = it },
                        label = "Species",
                        leadingIcon = Icons.Default.WbSunny,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = "Weight",
                        leadingIcon = Icons.Default.Balance,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = birthdate,
                        onValueChange = { birthdate = it },
                        label = "Birthdate",
                        leadingIcon = Icons.Default.Schedule,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val updatedPet = PetRequest(
                                name = name,
                                breed = breed,
                                species = species,
                                weight = weight.toFloat(),
                                birthdate = birthdate,
                                image_url = newImageUri.value?.toString() ?: petValue.image_url,
                                gender = petValue.gender.toString()
                            )

                            PetRepository().updatePet(petId, updatedPet){
                                if(it){
                                    showSuccessDialog.value = true
                                }else{
                                    Log.d("EditPetDetail", "Error updating pet")
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
    if (showSuccessDialog.value) {
        SuccessDialog(
            onDismissRequest = { showSuccessDialog.value = false
                navController.navigateUp() },
            titleText = "Update Successful",
            messageText = "The pet profile has been updated successfully.",
            buttonText = "OK"
        )
    }
}
