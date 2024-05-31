package pe.edu.upc.upet.ui.screens.appointment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.DropdownMenuBox
import pe.edu.upc.upet.ui.shared.ExpandableTextField
import pe.edu.upc.upet.ui.shared.IconAndTextHeader
import pe.edu.upc.upet.ui.theme.BorderPadding

@Composable
fun PetDetailsAppointmentScreen(navController: NavController) {
    val petOptions = listOf("Fido", "Abogato", "Figaro", "Tom", "Beily", "CarlosIII", "Poly")
    val selectedPet = remember { mutableStateOf(petOptions[0]) }
    val textproblem = remember { mutableStateOf("") }

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

            DropdownMenuBox(options = petOptions, selectedOption = selectedPet, paddingStart = 0, paddingEnd = 0, fontSize = 16)

            TextSubtitle(text = "Pet's problem")

            ExpandableTextField(input = textproblem, placeholder = "Enter your pet's problem")
        }

        CustomButton(text = "Book now", onClick = {navController.navigate(Routes.AppointmentList)})

        Spacer(modifier = Modifier.height(20.dp))

    }

}

