package pe.edu.upc.upet.ui.screens.ownerviews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import pe.edu.upc.upet.feature_profile.data.repository.PetOwnerRepository
import pe.edu.upc.upet.feature_profile.domain.PetOwner
import pe.edu.upc.upet.utils.TokenManager

@Composable
fun getOwner(): PetOwner? {
    val (id, _, _) = TokenManager.getUserIdAndRoleFromToken() ?: return null

    val owner = remember {
        mutableStateOf<PetOwner?>(null)
    }

    LaunchedEffect(id) {
        PetOwnerRepository().getPetOwnerById(id) { fetchedOwner ->
            owner.value = fetchedOwner
        }
    }


    return owner.value
}

