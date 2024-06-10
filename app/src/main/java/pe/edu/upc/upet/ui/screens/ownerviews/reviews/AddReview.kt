package pe.edu.upc.upet.ui.screens.ownerviews.reviews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.edu.upc.upet.feature_profile.data.repository.PetOwnerRepository
import pe.edu.upc.upet.feature_profile.domain.PetOwner
import pe.edu.upc.upet.feature_reviews.data.remote.ReviewRequest
import pe.edu.upc.upet.feature_reviews.data.repository.ReviewRepository
import pe.edu.upc.upet.ui.screens.ownerviews.getOwner
import pe.edu.upc.upet.utils.TokenManager

@Composable
fun AddReview(navController: NavController, vetId: Int) {
    val (id, _, _) = TokenManager.getUserIdAndRoleFromToken() ?: error("Error obteniendo el userId y userRole desde el token")

    var owner by remember { mutableStateOf<PetOwner?>(null) }
    PetOwnerRepository().getPetOwnerById(id) { fetchedOwner ->
        owner = fetchedOwner
    }

    var reviewContent by remember { mutableStateOf("") }
    var reviewRating by remember { mutableStateOf("") }

    var snackbarMessage by remember { mutableStateOf("") }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = reviewContent,
                onValueChange = { reviewContent = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            TextField(
                value = reviewRating,
                onValueChange = { reviewRating = it },
                label = { Text("Rating") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    val reviewRequest = ReviewRequest(
                        stars = reviewRating.toInt(),
                        description = reviewContent,
                        veterinarianId = vetId,
                    )
                    ReviewRepository().createReview(owner?.id ?: -1, reviewRequest, onSuccess = {
                        navController.popBackStack()
                    }, onError = {
                        snackbarMessage = "Error al Crear el review"
                    })

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Review")
            }

            if (snackbarMessage.isNotBlank()) {
                Text(
                    text = snackbarMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
