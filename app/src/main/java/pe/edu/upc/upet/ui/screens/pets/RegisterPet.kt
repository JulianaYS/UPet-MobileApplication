package pe.edu.upc.upet.ui.screens.pets

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_pet.data.remote.GenderEnum
import pe.edu.upc.upet.feature_pet.data.remote.PetRequest
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.Dialog
import pe.edu.upc.upet.ui.shared.InputDate
import pe.edu.upc.upet.ui.shared.InputDropdownField
import pe.edu.upc.upet.ui.shared.LabelTextField
import pe.edu.upc.upet.ui.shared.RadioButtonsOptions
import pe.edu.upc.upet.ui.shared.TextFieldType
import pe.edu.upc.upet.ui.shared.uploadImage
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary
import pe.edu.upc.upet.ui.theme.poppinsFamily
import pe.edu.upc.upet.utils.TokenManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterPet(navController: NavHostController) {
    val (id, _, _) = TokenManager.getUserIdAndRoleFromToken() ?: error("Error obteniendo el userId y userRole desde el token")
    val showSuccessDialog = remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        val name = remember { mutableStateOf("") }
        val breed = remember { mutableStateOf("") }
        val weight = remember { mutableStateOf("") }
        val imageUrl = remember { mutableStateOf<Uri?>(null) }
        val uploadedImageUrl = remember { mutableStateOf("") }
        val selectedDate = remember { mutableStateOf("") }
        val selectedGender = remember { mutableIntStateOf(0) }
        val showErrorSnackbar = remember { mutableStateOf(false) }
        val snackbarMessage = remember { mutableStateOf("") }



        val typeOptions = listOf("Dog", "Cat", "Bird", "Fish", "Reptile", "Rodent", "Rabbit", "Other")
        val selectedType = remember { mutableStateOf(typeOptions[0]) }

        val pickImage = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUrl.value = uri
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(UpetBackGroundPrimary),
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0B1C3F))
                            .padding(top = 10.dp, start = BorderPadding, end = BorderPadding),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CustomReturnButton(navController = navController)
                        Text(
                            text = "Pet Form",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, start = 20.dp),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 20.sp,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.SemiBold
                            ),
                        )
                    }


                    PetImageRegister(
                        text = "Upload image",
                        onPickImageClick = { pickImage.launch("image/*") },
                        imageUrl = imageUrl.value.toString()
                    )

                    AuthInputTextField(
                        input = name,
                        placeholder = "Enter your pet's name",
                        label = "Pet Name"
                    )

                    InputDate(text = "Date of birth", onDateSelected = { date ->
                        selectedDate.value = date
                    })

                    InputDropdownField(
                        label = "Type of Animal",
                        options = typeOptions,
                        selectedOption = selectedType,
                    )
                    AuthInputTextField(
                        input = breed,
                        placeholder = "Enter your pet's breed",
                        label = "Breed"
                    )
                    AuthInputTextField(
                        input = weight,
                        placeholder = "Enter your pet's weight in Kg",
                        label = "Weight (Kg)",
                        type = TextFieldType.Phone
                    )
                    GenderOption(selectedGender)
                    AuthButton(text = "Save", onClick = {
                        if (name.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your pet's name."
                            showErrorSnackbar.value = true
                        } else if (selectedDate.value.isEmpty()) {
                            snackbarMessage.value = "You must enter a pet's date of birth."
                            showErrorSnackbar.value = true
                        } else if (selectedType.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your pet's type."
                            showErrorSnackbar.value = true
                        } else if (breed.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your pet's breed."
                            showErrorSnackbar.value = true
                        } else if(weight.value.isEmpty()){
                            snackbarMessage.value = "You must enter your pet's weight."
                            showErrorSnackbar.value = true
                        } else if(imageUrl.value == null){
                            snackbarMessage.value = "You must upload an image."
                            showErrorSnackbar.value = true
                        } else {
                            // Upload the image to Cloudinary when "Save" is clicked
                            imageUrl.value?.let { uri ->
                                uploadImage(uri) { url, error ->
                                    if (error != null) {
                                        snackbarMessage.value =
                                            "Failed to upload image. ${error.message}"
                                        showErrorSnackbar.value = true
                                    } else {
                                        uploadedImageUrl.value = url ?: ""
                                        val currentFormatter =
                                            DateTimeFormatter.ofPattern("d/M/yyyy")
                                        val requiredFormatter =
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                        val date =
                                            LocalDate.parse(selectedDate.value, currentFormatter)
                                        val formattedDate = date.format(requiredFormatter)

                                        PetRepository().createPet(
                                            id,
                                            PetRequest(
                                                name = name.value,
                                                breed = breed.value,
                                                species = selectedType.value,
                                                weight = weight.value.toFloat(),
                                                birthdate = formattedDate,
                                                image_url = uploadedImageUrl.value,
                                                gender = if (selectedGender.intValue == 0) GenderEnum.Male.toString() else GenderEnum.Female.toString()
                                            ),
                                            onSuccess = { success ->
                                                if (success.id != 0) {
                                                    snackbarMessage.value =
                                                        "Pet registered successfully."
                                                    showErrorSnackbar.value = false
                                                    showSuccessDialog.value = true
                                                } else {
                                                    snackbarMessage.value =
                                                        "Failed to register pet."
                                                    showErrorSnackbar.value = true
                                                }
                                            },
                                            onError = { err ->
                                                snackbarMessage.value =
                                                    "Failed to register pet: $err"
                                                showErrorSnackbar.value = true
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    })
                }
            }
        }
        }
        Dialog(message = snackbarMessage.value, showError = showErrorSnackbar)
    }

    if (showSuccessDialog.value) {

        SuccessDialog(onDismissRequest = {
            showSuccessDialog.value = false
            navController.navigate(Routes.Home)
        }, titleText = "Pet Registered",
            messageText = "Your pet has been registered successfully.",
            buttonText = "OK")

    }
}




@Composable
fun GenderOption( selectedOption: MutableState<Int> = mutableIntStateOf(1)){
    Column (
        modifier = Modifier.padding(
            start = BorderPadding,
            end = BorderPadding,
            top = 4.dp,
            bottom = 4.dp
        )
    ){
        Text(text ="Gender", style = TextStyle(
            color = Color.White,
            fontSize = 12.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium
        )
        )
        RadioButtonsOptions(
            option1 = "Male",
            option2 = "Female",
            selectedOption = selectedOption
        )
    }
}

@Composable
fun PetImageRegister(text: String, onPickImageClick: () -> Unit, imageUrl: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val commonPadding = BorderPadding
        LabelTextField(text, commonPadding)
        Box(
            modifier = Modifier
                .size(110.dp)
                .border(3.dp, Pink, RoundedCornerShape(4.dp))
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0x10FFFFFF))
                .align(Alignment.CenterHorizontally)
                .clickable(onClick = onPickImageClick), // Add clickable to the Box
            contentAlignment = Alignment.Center
        ) {

                IconButton(onClick = onPickImageClick) {
                    Icon(Icons.Filled.Upload, contentDescription = "Upload Icon", tint = Pink)
                }

            GlideImage(
                imageModel = { imageUrl },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
fun SuccessDialog(
    onDismissRequest: () -> Unit,
    titleText: String,
    messageText: String,
    buttonText: String
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = null,
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    Icons.Filled.CheckCircleOutline,
                    "Success Icon",
                    modifier =  Modifier.size(64.dp),
                    tint = Color(0xFF4CAF50)
                )
                Text(
                    text = titleText,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF4CAF50)
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = messageText,
                    style = TextStyle(fontSize = 16.sp, color = Color.Black),
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text(buttonText, color = Color.White)
                }
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxSize(0.8f).padding(vertical = 150.dp)
    )
}