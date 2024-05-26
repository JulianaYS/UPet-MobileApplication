package pe.edu.upc.upet.ui.screens.auth.signin


import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import pe.edu.upc.upet.feature_auth.data.repository.AuthRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthHeader
import pe.edu.upc.upet.ui.shared.AuthTextButton
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.Dialog
import pe.edu.upc.upet.ui.shared.TextFieldType
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary
import pe.edu.upc.upet.ui.theme.UpetOrange1
import pe.edu.upc.upet.utils.TokenManager


@Composable
fun SignInScreen(authRepository: AuthRepository = AuthRepository(), navigateTo: (String) -> Unit){

    val context = LocalContext.current
    Scaffold {paddingValues->
        val email = remember{
            mutableStateOf("")
        }
        val password = remember{
            mutableStateOf("")
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
                    AuthHeader(texto = "Login")
                    HorizontalDivider(
                        modifier = Modifier.padding(BorderPadding),
                        thickness = 30.dp,
                        color = Color.Transparent
                    )
                    AuthInputTextField(
                        input = email,
                        placeholder = "Enter your email",
                        label = "Email"
                    )
                    AuthInputTextField(
                        input = password,
                        placeholder = "Enter your password",
                        label = "Password",
                        type=TextFieldType.Password
                    )
                    email.value = "juan@gmail.com"
                    password.value = "juan"
                    AuthTextButton("Forgot Password?", arrangement = Arrangement.End,
                        onClickClickableText = {
                            navigateTo(Routes.PasswordRecovery)
                        })
                    AuthButton(text = "Log In", onClick = {
                        if (email.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your email."
                            showErrorSnackbar.value = true
                        } else if(!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()){
                            snackbarMessage.value = "You must enter a valid email."
                            showErrorSnackbar.value = true
                        } else if (password.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your password."
                            showErrorSnackbar.value = true
                        } else {
                            authRepository.signIn( email.value, password.value) { success ->
                                if (success) {
                                    Log.d("SuccesSignIn", "User authenticated")
                                    navigateTo(Routes.PostRegister)
                                } else {
                                    snackbarMessage.value = "Invalid credentials."
                                    showErrorSnackbar.value = true
                                }
                            }

                        }

                    })
                    HorizontalDivider(
                        modifier = Modifier.padding(BorderPadding),
                        thickness = 1.dp,
                        color = UpetOrange1
                    )
                    AuthTextButton(
                        "Register Now",
                        text = "New member?",
                        onClickClickableText = {
                            navigateTo(Routes.UserRegister)
                        },
                    )
                }

                Dialog(message = (snackbarMessage.value), showError = showErrorSnackbar )
            }

        }
    }
}


fun signInUser(navigateTo: (String) -> Unit){
    if (TokenManager.isUserAuthenticated()) {
        navigateTo(Routes.Home)
    } else {
        Log.d("tag", "User not authenticated")
    }
}