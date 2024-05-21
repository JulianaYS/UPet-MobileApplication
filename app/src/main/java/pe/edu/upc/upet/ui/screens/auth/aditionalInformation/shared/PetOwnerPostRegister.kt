package pe.edu.upc.upet.ui.screens.auth.aditionalInformation.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pe.edu.upc.upet.feature_profile.data.remote.PetOwnerRequest
import pe.edu.upc.upet.feature_profile.data.repository.PetOwnerRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.TextFieldType

@Composable
fun PetOwnerPostRegister(navigateTo: (String) -> Unit={}, userId : Int){
    PostRegister(
        description = { PostRegisterDescription(
            title = "Aditional Information",
            description = "Add location and phone number for enhanced pet care services."
        ) },
        form = { PetOwnerPostRegisterForm(navigateTo, userId) }
    )

}
@Composable
fun PetOwnerPostRegisterForm(navigateTo: (String) -> Unit, userId : Int){
    Column(

    ){
        val numberPhone = remember { mutableStateOf("") }
        AuthInputTextField(
            input = numberPhone,
            placeholder = "Enter your phone number",
            label = "Phone",
            type = TextFieldType.Phone
        )
        Spacer(modifier = Modifier.height(22.dp))
        AuthInputTextField(
            input = remember { mutableStateOf("") },
            placeholder = "r. Lima 104, Santiago de Surco, Lima",
            label = "Location",
            type = TextFieldType.Text
        )
        Spacer(modifier = Modifier.height(22.dp))
        AuthButton(text ="Send",
            onClick = {
                createNewPetOwner(userId, PetOwnerRequest(
                    numberPhone = numberPhone.value,
                ))
                navigateTo(Routes.Home)
        })
    }

}

fun createNewPetOwner(userId: Int,
                      petOwnerData: PetOwnerRequest,
                      petOwnerRepository: PetOwnerRepository= PetOwnerRepository()
){
    petOwnerRepository.createPetOwner(userId, petOwnerData) {
        println("Pet Owner created")
    }
}