package pe.edu.upc.upet.ui.screens.auth.signin


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import pe.edu.upc.upet.feature_auth.data.repository.AuthRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthHeader
import pe.edu.upc.upet.ui.shared.AuthTextButton
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary
import pe.edu.upc.upet.ui.theme.UpetOrange1


@Composable
fun SignInScreen(authRepository: AuthRepository = AuthRepository(), navigateTo: (String) -> Unit){
    Scaffold {paddingValues->
        val email = remember{
            mutableStateOf("")
        }
        val password = remember{
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(UpetBackGroundPrimary)
        ){

            AuthHeader(texto = "Login")
            HorizontalDivider(
                modifier = Modifier.padding(BorderPadding),
                thickness = 30.dp,
                color = Color.Transparent
            )
            AuthInputTextField(input = email, placeholder = "Enter your email", label ="Email")
            AuthInputTextField(input = password, placeholder = "Enter your password", label ="Password", true)
            AuthTextButton("Forgot Password?", arrangement = Arrangement.End,
                onClickClickableText = {
                    navigateTo(Routes.PasswordRecovery)
                })
            AuthButton(text = "Log In", onClick = {
                navigateTo(Routes.Home)
            })
            HorizontalDivider(
                modifier = Modifier.padding(BorderPadding),
                thickness = 1.dp,
                color = UpetOrange1
            )
            AuthTextButton("Register Now",
                text ="New member?",
                onClickClickableText = {
                    navigateTo(Routes.UserRegister)
                },)

        }
    }
}


