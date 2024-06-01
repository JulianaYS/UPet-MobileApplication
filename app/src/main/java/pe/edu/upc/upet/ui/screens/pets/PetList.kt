package pe.edu.upc.upet.ui.screens.pets
import android.util.Log
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
import androidx.navigation.NavHostController
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.PetSwipeToDelete
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.utils.TokenManager


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PetList(navController: NavHostController) {

    val petRepository = remember { PetRepository() }
    val petsState = remember {
        mutableStateOf<List<PetResponse>>(emptyList())
    }

    val (id, _, _) = TokenManager.getUserIdAndRoleFromToken() ?: error("Error obteniendo el userId y userRole desde el token")

    petRepository.getPetsByOwnerId(id,
            onSuccess = { pets ->
                petsState.value = pets
            },
            onError = {

            }
    )


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
                PetSwipeToDelete(navController, pet, deletePet = {
                    petRepository.deletePet(pet.id,
                        onSuccess = {
                            petsState.value = petsState.value.filter { it != pet }
                        },
                        onError = {
                            Log.d("PetList", "Error al eliminar la mascota")
                        }
                    )
                })
            }
        }
    }
}



