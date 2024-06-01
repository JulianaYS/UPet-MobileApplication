package pe.edu.upc.upet.ui.screens.appointment

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.edu.upc.upet.feature_appointment.data.remote.AppointmentRequest
import pe.edu.upc.upet.feature_appointment.data.repository.AppointmentRepository
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.pets.SuccessDialog
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.DropdownMenuBox
import pe.edu.upc.upet.ui.shared.ExpandableTextField
import pe.edu.upc.upet.ui.shared.IconAndTextHeader
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.utils.TokenManager

@Composable
fun PetDetailsAppointmentScreen(navController: NavController, vetClinicId: Int, selectedDate: String, selectedTime: String) {
    val petRepository = remember { PetRepository() }
    var petOptions: List<PetResponse> by remember { mutableStateOf(emptyList()) }
    val showSuccessDialog = remember { mutableStateOf(false) }
    val ownerId = TokenManager.getUserIdAndRoleFromToken()?.first
    val selectedPet = remember { mutableStateOf("") }
    val textProblem = remember { mutableStateOf("") }

    LaunchedEffect(key1 = petRepository) {
        ownerId?.let { id ->
            petRepository.getPetsByOwnerId(id, { pets ->
                petOptions = pets
                if (pets.isNotEmpty()) {
                    selectedPet.value = pets[0].name
                }
            }, { error ->
                Log.d("Error", error)
            })
        }
    }

    if (showSuccessDialog.value) {
        SuccessDialog(onDismissRequest = {
            showSuccessDialog.value = false
            navController.navigate(Routes.AppointmentList)
        }, titleText = "Pet Registered",
            messageText = "Your pet has been registered successfully.",
            buttonText = "OK")
    }

    Column(
        modifier = Modifier
            .padding()
            .fillMaxSize()
            .padding(top = 10.dp, start = BorderPadding, end = BorderPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            verticalArrangement = Arrangement.Top
        ){
            IconAndTextHeader(onBackClick = { navController.popBackStack() }, text = "Pet details")
            TextSubtitle(text = "Select my pet")
            DropdownMenuBox(options = petOptions.map { it.name }, selectedOption = selectedPet, paddingStart = 0, paddingEnd = 0, fontSize = 16)
            TextSubtitle(text = "Pet's problem")
            ExpandableTextField(input = textProblem, placeholder = "Enter your pet's problem")
        }

        CustomButton(text = "Book now", onClick = {

            createAppointment(vetClinicId, selectedDate, selectedTime, textProblem.value, petOptions, selectedPet.value, showSuccessDialog)
        })

        Spacer(modifier = Modifier.height(20.dp))
    }
}

private fun createAppointment(vetClinicId: Int, selectedDate: String, selectedTime: String, problem: String, petOptions: List<PetResponse>, selectedPetName: String, showSuccessDialog: MutableState<Boolean>) {
    val appointment = AppointmentRequest(
        datetime = "${selectedDate}T${selectedTime}",
        diagnosis = "",
        treatment = "",
        description = problem,
        petId = petOptions.find { it.name == selectedPetName }?.id ?: 0,
        veterinarianId = vetClinicId
    )
    AppointmentRepository().createAppointment(vetClinicId, appointment, onSuccess = {
        showSuccessDialog.value = true
        Log.d("Success", it.toString())
    }, onError = {
        Log.d("Error", it)
    })
}