package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.edu.upc.upet.feature_pet.domain.Pet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetSwipeToDelete(pet: Pet, deletePet:() ->Unit, onPetSelected: (Int) -> Unit) {
    val dismissState = rememberSwipeToDismissBoxState()

    LaunchedEffect(key1 = dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd) {

            deletePet()

        }
        dismissState.reset()
    }
    SwipeToDismissBox(
    state = dismissState,
    backgroundContent = { SwipeToDismissBackground() },
    content = { PetCard(pet = pet, onPetSelected) },
    modifier = Modifier
    .fillMaxWidth()
    .padding(15.dp)
    .clip(RoundedCornerShape(20.dp)),
    enableDismissFromEndToStart = false,
    enableDismissFromStartToEnd = true,
    )
}