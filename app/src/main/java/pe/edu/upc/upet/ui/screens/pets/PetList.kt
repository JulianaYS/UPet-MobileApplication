package pe.edu.upc.upet.ui.screens.pets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import pe.edu.upc.upet.feature_pet.domain.pets
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.PetSwipeToDelete
import pe.edu.upc.upet.ui.theme.Blue1

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PetList(navController: NavController) {

    val petsState = remember { mutableStateOf(pets) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue1,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text("My Pets")
                },
                navigationIcon = {
                    CustomReturnButton(navController = navController)
                }
            )
        }
    ) {paddingValues->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(petsState.value) { pet ->
                PetSwipeToDelete(pet, deletePet = {
                    petsState.value = petsState.value.filter { it != pet }
                },  onPetSelected = {
                    navController.navigate("PetProfile/${pet.id}")
                })
            }
        }
    }
}


