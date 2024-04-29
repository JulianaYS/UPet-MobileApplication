package pe.edu.upc.upet.feature_auth.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.shared.ui.Auth.AuthButton
import pe.edu.upc.upet.shared.ui.Auth.AuthCheckBox
import pe.edu.upc.upet.shared.ui.Auth.AuthHeader
import pe.edu.upc.upet.shared.ui.Auth.AuthTextButton
import pe.edu.upc.upet.shared.ui.Auth.InputTextField
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.UPetTheme
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary
import pe.edu.upc.upet.ui.theme.UpetOrange1
import pe.edu.upc.upet.ui.theme.poppinsFamily


@Composable
fun SignUpScreen(){
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
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(UpetBackGroundPrimary)
        ){

            AuthHeader(texto = "Register")
            InputTextField(input = fullName, placeholder = "Enter your full name", label ="Full Name" )
            InputTextField(input = email, placeholder = "Enter your email", label ="Email" )
            InputTextField(input = password, placeholder = "Enter your password", label ="Password" )
            AuthUserRolCheckBox()
            AuthCheckBox(checkedState = checkedState)
            AuthButton(text = "Register")
            AuthTextButton(text= "Already member?", clickableText ="Login")

        }
    }
}

@Composable
fun AuthUserRolCheckBox(){
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
        RadioButtons()
    }
}
data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String
)

@Composable
private fun RadioButtons() {
    val radioButtons = remember {
        mutableStateListOf(
            ToggleableInfo(
                isChecked = true,
                text = "Veterinarian"
            ),
            ToggleableInfo(
                isChecked = false,
                text = "Pet Owner"
            )
        )
    }

    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        radioButtons.forEachIndexed { index, info ->
            Row(
                modifier = Modifier
                    .clickable {
                        radioButtons.replaceAll {
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
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
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = UpetOrange1,
                        unselectedColor = UpetOrange1
                    ),
                )
                Text(text = info.text, style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium
                ),
                    modifier = Modifier.padding(top=15.dp))
            }
        }
    }
}

@Preview
@Composable
fun SignUpPreview(){
    UPetTheme {
        SignUpScreen()
    }
}