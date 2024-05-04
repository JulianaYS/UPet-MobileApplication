package pe.edu.upc.upet.ui.screens.auth.signup

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.feature_auth.data.remote.UserRequest
import pe.edu.upc.upet.feature_auth.data.remote.UserType
import pe.edu.upc.upet.feature_auth.data.repository.AuthRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthCheckBox
import pe.edu.upc.upet.ui.shared.AuthHeader
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.AuthTextButton
import pe.edu.upc.upet.ui.shared.Dialog
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary
import pe.edu.upc.upet.ui.theme.UpetOrange1
import pe.edu.upc.upet.ui.theme.poppinsFamily


@Composable
fun SignUpScreen( navigateTo: (String) -> Unit ){

    Scaffold {paddingValues->
        val fullName = remember {
            mutableStateOf("")
        }
        val email = remember{
            mutableStateOf("")
        }
        val password = remember{
            mutableStateOf("")
        }
        val checkedState = remember {
            mutableStateOf(false)
        }
        val selectedOption = remember {
            mutableIntStateOf(1)
        }

        val showErrorSnackbar = remember { mutableStateOf(false) }
        val snackbarMessage = remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(UpetBackGroundPrimary)
        ){

            Box {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(UpetBackGroundPrimary)
                ) {
                    AuthHeader(texto = "Register")
                    AuthInputTextField(input = fullName, placeholder = "Enter your full name", label ="Full Name" )
                    AuthInputTextField(input = email, placeholder = "Enter your email", label ="Email" )
                    AuthInputTextField(input = password, placeholder = "Enter your password", label ="Password", true )
                    AuthUserRolCheckBox(selectedOption = selectedOption)
                    AuthCheckBox(checkedState = checkedState)
                    AuthButton(text = "Register", onClick = {
                        if (fullName.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your full name."
                            showErrorSnackbar.value = true
                        } else if (email.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your email."
                            showErrorSnackbar.value = true
                        } else if(!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()){
                            snackbarMessage.value = "You must enter a valid email."
                            showErrorSnackbar.value = true
                        } else if (password.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your password."
                            showErrorSnackbar.value = true
                        } else if (!checkedState.value) {
                            snackbarMessage.value = "You must accept the Terms and Conditions."
                            showErrorSnackbar.value = true
                        } else {
                            registerLogicButton(userRequest = UserRequest(
                                name = fullName.value,
                                email = email.value,
                                password = password.value,
                                userType = if (selectedOption.value == 1) UserType.Vet else UserType.Owner
                            ))
                            navigateTo(Routes.Home)
                        }
                    })

                    AuthTextButton(text= "Already member?",
                        clickableText ="Login",
                        onClickClickableText = {
                            navigateTo(Routes.UserLogin)
                        } )
                }

                Dialog(message = (snackbarMessage.value), showError = showErrorSnackbar )

            }

        }
    }
}

@Composable
fun AuthUserRolCheckBox( selectedOption: MutableState<Int> = mutableIntStateOf(1)){
    Column (
        modifier = Modifier.padding(
            start = BorderPadding,
            end = BorderPadding,
            top = 4.dp,
            bottom = 4.dp
        )
    ){
        Text(text ="Are you a ...", style = TextStyle(
            color = Color.White,
            fontSize = 12.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium
        ))
        RadioButtons(
            option1 = "Veterinarian",
            option2 = "Pet Owner",
            selectedOption = selectedOption
        )
    }
}
data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String
)

@Composable
private fun RadioButtons(
    option1: String = "Veterinarian",
    option2: String = "Pet Owner",
    selectedOption: MutableState<Int>
) {
    val radioButtons = remember {
        mutableStateListOf(
            ToggleableInfo(
                isChecked = true,
                text = option1
            ),
            ToggleableInfo(
                isChecked = false,
                text = option2
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        radioButtons.forEachIndexed { index, info ->
            Row(
                modifier = Modifier
                    .clickable {
                        radioButtons.replaceAll {
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
                        selectedOption.value =
                            if (index == 0) 1 else 2 // Asigna 1 si es la opción 1, 2 si es la opción 2
                    }
                    .padding(end = 10.dp)
            ) {
                RadioButton(
                    selected = info.isChecked,
                    onClick = {
                        radioButtons.replaceAll {
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
                        selectedOption.value = if (index == 0) 1 else 2 // Asigna 1 si es la opción 1, 2 si es la opción 2
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = UpetOrange1,
                        unselectedColor = UpetOrange1
                    ),
                )
                Text(
                    text = info.text,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
        }
    }
}


fun registerLogicButton(authRepository: AuthRepository= AuthRepository(), userRequest: UserRequest){
    authRepository.signUp(userRequest, {})
}

