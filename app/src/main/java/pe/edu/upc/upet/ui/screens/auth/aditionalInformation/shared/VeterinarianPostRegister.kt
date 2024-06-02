package pe.edu.upc.upet.ui.screens.auth.aditionalInformation.shared

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pe.edu.upc.upet.feature_vetClinics.data.remote.VetRequest
import pe.edu.upc.upet.feature_vetClinics.data.remote.VeterinaryClinicRequest
import pe.edu.upc.upet.feature_vetClinics.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic
import pe.edu.upc.upet.feature_vets.data.repository.VetRepository
import pe.edu.upc.upet.navigation.Routes

import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.RadioButtonsOptions
import pe.edu.upc.upet.ui.shared.TextFieldType

@Composable
fun VeterinarianPostRegister(navigateTo : (String) -> Unit, userId: Int){
    PostRegister(
        description = { PostRegisterDescription(
            title = "Select Veterinary Clinic",
            description = "Do you want to register a new veterinary clinic or join an existing clinic?"
        ) },
        form = { VeterinarianPostRegisterForm(navigateTo,userId) }
    )

}
@Composable
fun VeterinarianPostRegisterForm(navigateTo : (String) -> Unit, userId: Int){
    val  selectedOption = remember{
        mutableIntStateOf(1)
    }
    Column {
        RadioButtonsOptions(
            option1 = "New  Clinic",
            option2 = "Existing Clinic",
            selectedOption = selectedOption
        )
        Spacer(modifier = Modifier.height(22.dp))
        if(selectedOption.value == 1)
            NewClinicForm(navigateTo, userId)
        else
            ExistingClinicForm(navigateTo,userId)
    }

}
@Composable
fun ExistingClinicForm(navigateTo : (String) -> Unit,
                       userId: Int,
                        veterinaryClinicRepository: VeterinaryClinicRepository = VeterinaryClinicRepository()
                       ){

    val clinics = remember {
        mutableStateOf(emptyList<VeterinaryClinic>())
    }
    veterinaryClinicRepository.getAllVeterinaryClinics { vets ->
        clinics.value = vets
    }
    val selectedClinics = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    AuthInputTextField(
        input = selectedClinics,
        placeholder = "Select your clinic",
        label = "Clinic",
        type = TextFieldType.Dropdown,
        dropdownList = clinics.value.map { it.name },
    )
    Spacer(modifier = Modifier.height(22.dp))
    AuthInputTextField(
        input = password,
        placeholder = "Enter the clinic password",
        label = "Password",
        type = TextFieldType.Password
    )
    Spacer(modifier = Modifier.height(22.dp))

    AuthButton(text ="Send", onClick = {
        createVeterinary(
            userId,
            VetRequest(
                selectedClinics.value,
                password.value
            ),
            navigateTo = { navigateTo(Routes.Home)}
        )

    })

}
@Composable
fun NewClinicForm(navigateTo : (String) -> Unit, userId: Int) {


    val clinicName = remember { mutableStateOf("") }
    val clinicLocation = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") } // new field
    val officeHoursStart = remember { mutableStateOf("") } // new field
    val officeHoursEnd = remember { mutableStateOf("") } // new field

    LazyColumn {
        item {
            AuthInputTextField(
                input = clinicName,
                placeholder = "Enter your clinic name",
                label = "Clinic Name",
            )
            Spacer(modifier = Modifier.height(22.dp))
            AuthInputTextField(
                input = clinicLocation,
                placeholder = "r. Lima 104, Santiago de Surco, Lima",
                label = "Location",
            )

            Spacer(modifier = Modifier.height(22.dp))
            AuthInputTextField(
                input = phoneNumber,
                placeholder = "Enter your phone number",
                label = "Phone",
                type = TextFieldType.Phone
            )
            Spacer(modifier = Modifier.height(22.dp))
            AuthInputTextField( // new field
                input = description,
                placeholder = "Enter your clinic description",
                label = "Description",
            )
            Spacer(modifier = Modifier.height(22.dp))
            AuthInputTextField( // new field
                input = officeHoursStart,
                placeholder = "Enter your office hours start time",
                label = "Office Hours Start",
            )
            Spacer(modifier = Modifier.height(22.dp))
            AuthInputTextField( // new field
                input = officeHoursEnd,
                placeholder = "Enter your office hours end time",
                label = "Office Hours End",
            )
            Spacer(modifier = Modifier.height(22.dp))
            AuthButton(text ="Send", onClick = {
                createNewClinic(
                    userId,
                    VeterinaryClinicRequest(
                        name = clinicName.value,
                        location = clinicLocation.value,
                        phoneNumber = phoneNumber.value,
                        description = description.value, // pass the new field
                        officeHoursStart = officeHoursStart.value, // pass the new field
                        officeHoursEnd = officeHoursEnd.value, // pass the new field
                    ),
                    navigateTo = { navigateTo(Routes.Home) }
                )
            })
        }
    }
}


fun createVeterinary(
    userId: Int,
    vetRequest: VetRequest,
    vetRepository: VetRepository = VetRepository(),
    navigateTo: () -> Unit = {}
){
    vetRepository.createVet(userId, vetRequest){
        navigateTo()
    }
}
fun createNewClinic(userId: Int,
                    clinicRequest: VeterinaryClinicRequest,
                    clinicRepository : VeterinaryClinicRepository = VeterinaryClinicRepository(),
                    navigateTo: () -> Unit
){
    Log.d("ClinicRequest", userId.toString())
    Log.d("ClinicRequest", clinicRequest.name)
    Log.d("ClinicRequest", clinicRequest.location)
    Log.d("ClinicRequest", clinicRequest.phoneNumber)

    clinicRepository.createVeterinaryClinic(clinicRequest){ clinicResponse ->
        Log.d("ClinicResponse", clinicResponse.toString())
        val clinicId= clinicResponse?.id ?: error("Error creating clinic")
        val clinicName= clinicResponse.name
        Log.d("ClinicResponse", "Clinic created with id ${clinicId.toString()}")

        clinicRepository.generatePassword(clinicId){password->
            Log.d("Password", "Password generated: $password")
            createVeterinary(
                userId,
                VetRequest(
                    clinicName,
                    password.toString()
                ),
                navigateTo = navigateTo
            )
        }
    }
}