package pe.edu.upc.upet.ui.screens.ownerviews

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import pe.edu.upc.upet.feature_pet.data.remote.PetRequest
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.feature_profile.data.repository.PetOwnerRepository
import pe.edu.upc.upet.feature_profile.domain.PetOwner
import pe.edu.upc.upet.feature_vets.data.repository.VetRepository
import pe.edu.upc.upet.feature_vets.domain.Vet
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

@Composable
fun getVet(): Vet? {
    val (id, _, _) = TokenManager.getUserIdAndRoleFromToken() ?: return null
    val vet = remember {
        mutableStateOf<Vet?>(null)
    }

    LaunchedEffect(id) {
        VetRepository().getVetById(id) { fetchedVet ->
            vet.value = fetchedVet
        }
    }
    return vet.value
}

fun getRole(): String {
    val (_, role, _) = TokenManager.getUserIdAndRoleFromToken() ?: error("Error")
    return role
}


