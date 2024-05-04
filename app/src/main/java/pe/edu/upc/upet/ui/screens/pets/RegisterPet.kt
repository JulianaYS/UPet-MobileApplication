package pe.edu.upc.upet.ui.screens.pets

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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.InputTextField

@Composable
fun RegisterPet(navController: NavController) {
    Scaffold {paddingValues ->
        val name = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }

        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(10.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ){
            Row {
                CustomReturnButton(navController = navController)
                Text(
                    text = "Pet Form",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                )
            }

            PetImageRegister(url = "https://image.freepik.com/vector-gratis/ilustracion-vector-dibujos-animados-lindo-animal-mascota_24640-53565.jpg")
            TextRegisterPet(text = "Pet Name")
            InputTextField(input = email, placeholder = "Enter your pet's name")
            TextRegisterPet(text = "Date of birth")
            InputTextField(input = email, placeholder = "Enter your pet's name")
            TextRegisterPet(text = "Type of Animal")
            InputTextField(input = email, placeholder = "Enter your pet's name")
            TextRegisterPet(text = "Breed")
            InputTextField(input = email, placeholder = "Enter your pet's breed")
            TextRegisterPet(text = "Weight (Kg)")
            InputTextField(input = email, placeholder = "Enter your pet's weight in Kg")

            TextRegisterPet(text = "Gender")



            val radioOptions = listOf("Male", "Female")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
            Row(modifier = Modifier.selectableGroup().fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .width(IntrinsicSize.Min)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 8.dp),
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
            CustomButton(text = "Save") {}
        }
    }
}


@Composable
fun TextRegisterPet(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
}

@Composable
fun PetImageRegister(url: String) {
    Box(modifier = Modifier
        .size(70.dp)
        .clip(CircleShape),
        contentAlignment = Alignment.Center){
        GlideImage(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
            , imageModel = { url })

    }
}