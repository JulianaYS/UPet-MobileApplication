package pe.edu.upc.upet.ui.screens.pets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.auth.signup.RadioButtons
import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.InputDate
import pe.edu.upc.upet.ui.shared.InputDropdownField
import pe.edu.upc.upet.ui.shared.InputTextField
import pe.edu.upc.upet.ui.shared.LabelTextField
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary
import pe.edu.upc.upet.ui.theme.UpetOrange1
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun RegisterPet( navigateTo: (String) -> Unit) {
    Scaffold { paddingValues ->
        val name = remember { mutableStateOf("") }
        val type = remember { mutableStateOf("") }
        val breed = remember { mutableStateOf("") }
        val weight = remember { mutableStateOf("") }

        val selectedDate = remember { mutableStateOf("") }
        val selectedGender = remember {
            mutableIntStateOf(0)
        }

        val showErrorSnackbar = remember { mutableStateOf(false) }
        val snackbarMessage = remember { mutableStateOf("") }

        val typeOptions = listOf("Dogs", "Cats", "Birds")
        val selectedType = remember {
            mutableStateOf(typeOptions[0])
        }

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
                        CustomReturnButton1(onClick = { navigateTo(Routes.Home) })
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

                    PetImageRegister(text = "Upload image")

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
                        //placeholder = "Enter your pet's type",
                    )
                    AuthInputTextField(
                        input = breed,
                        placeholder = "Enter your pet's breed",
                        label = "Breed"
                    )
                    AuthInputTextField(   // que solo acepte numeros
                        input = weight,
                        placeholder = "Enter your pet's weight in Kg",
                        label = "Weight (Kg)",
                        keyboardType = KeyboardType.Number
                    )
                    GenderOption(selectedGender)
                    AuthButton(text = "Save", onClick = {
                        if (name.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your pet's name."
                            showErrorSnackbar.value = true
                        } else if (selectedDate.value.isEmpty()) {
                            snackbarMessage.value = "You must enter a pet's date of birth."
                            showErrorSnackbar.value = true
                        } else if (type.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your pet's type."
                            showErrorSnackbar.value = true
                        } else if (breed.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your pet's breed."
                            showErrorSnackbar.value = true
                        } else {
                            /*petRepository.registerPet(PetRequest(
                            name = name.value,
                            type = type.value,
                            breed = breed.value,
                            weight = weight.value.toDouble(),
                            gender = if (selectedGender.value == 1) GenderEnum.Male else GenderEnum.Female
                        )) { success ->
                            if (success) {
                                snackbarMessage.value = "Pet registered successfully."
                                showErrorSnackbar.value = false
                                navigateTo(Routes.Home)
                               } else {
                                   snackbarMessage.value = "Failed to register pet."
                                   showErrorSnackbar.value = true
                               }
                           }*/
                        }
                    })
                    //Divider(modifier = Modifier.height(10.dp))
                }
            }
        }

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
        RadioButtons(
            option1 = "Male",
            option2 = "Female",
            selectedOption = selectedOption
        )
    }
}



@Composable
fun CustomReturnButton1(onClick: ()-> Unit) {
    IconButton(
        modifier = Modifier
            .background(Color(0xFFFF6262), shape = CircleShape)
            .size(45.dp),
        onClick = onClick) {
        Icon(
            Icons.Filled.ArrowBackIosNew,
            "Back",
            tint = Color.White
        )
    }
}

@Composable
fun PetImageRegister(text: String) {
    Box(modifier = Modifier.fillMaxWidth())
    {
        val commonPadding = BorderPadding
        Row (verticalAlignment = Alignment.CenterVertically){
            LabelTextField(text, commonPadding)
            Icon(Icons.Filled.Upload, contentDescription = "Upload Icon", tint = UpetOrange1)
        }

    }
}