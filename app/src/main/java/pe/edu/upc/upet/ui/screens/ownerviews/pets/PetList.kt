package pe.edu.upc.upet.ui.screens.ownerviews.pets
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.ui.shared.PetSwipeToDelete
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.utils.TokenManager


@Composable
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
            TopBar(navController = navController, title = "My Pets")
        },
        modifier = Modifier.padding(16.dp)

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



