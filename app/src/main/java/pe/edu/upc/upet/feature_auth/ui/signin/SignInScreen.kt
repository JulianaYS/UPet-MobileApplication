package pe.edu.upc.upet.feature_auth.ui.signin


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.R
import pe.edu.upc.upet.shared.ui.Auth.AuthButton
import pe.edu.upc.upet.shared.ui.Auth.AuthHeader
import pe.edu.upc.upet.shared.ui.Auth.AuthTextButton
import pe.edu.upc.upet.shared.ui.Auth.InputTextField
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.UPetTheme
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary
import pe.edu.upc.upet.ui.theme.UpetOrange1
import pe.edu.upc.upet.ui.theme.poppinsFamily


@Composable
fun SingInScreen(){
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
            Divider(
                color = Color.Transparent,
                thickness = 30.dp,
                modifier = Modifier.padding(BorderPadding)
            )
            InputTextField(input = email, placeholder = "Enter your email", label ="Email")
            InputTextField(input = password, placeholder = "Enter your password", label ="Password", true)
            AuthTextButton("Forgot Password?", arrangement = Arrangement.End)
            AuthButton(text = "Log In")
            Divider(
                color = UpetOrange1,
                thickness = 1.dp,
                modifier = Modifier.padding(BorderPadding)
            )
            AuthTextButton("Register Now", text ="New member?")

        }
    }
}

@Preview
@Composable
fun SignInScrenPreview(){
    UPetTheme {
        SingInScreen()
    }
}