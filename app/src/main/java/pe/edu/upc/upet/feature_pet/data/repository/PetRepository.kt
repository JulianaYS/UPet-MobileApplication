package pe.edu.upc.upet.feature_pet.data.repository

import android.util.Log
import pe.edu.upc.upet.feature_pet.data.remote.PetRequest
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import pe.edu.upc.upet.feature_pet.data.remote.PetService
import pe.edu.upc.upet.feature_pet.data.remote.PetServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class PetRepository(private val petService: PetService = PetServiceFactory.getPetService()){

    fun getPetsByOwnerId(ownerId: Int, onSuccess: (List<PetResponse>) -> Unit, onError: (String) -> Unit){
        petService.getByOwnerId(ownerId).enqueue(object : Callback<List<PetResponse>> {
            override fun onResponse(call: Call<List<PetResponse>>, response: Response<List<PetResponse>>) {
                if (response.isSuccessful) {
                    val pets = response.body()?.map { petResponse ->
                        PetResponse(
                            id = petResponse.id,
                            name = petResponse.name,
                            petOwnerId = petResponse.petOwnerId,
                            breed = petResponse.breed,
                            species = petResponse.species,
                            weight = petResponse.weight,
                            birthdate = petResponse.birthdate,
                            image_url = petResponse.image_url,
                            gender = petResponse.gender
                        )
                    } ?: emptyList()
                    onSuccess(pets)
                } else {
                    onError("Error")
                }
            }

            override fun onFailure(call: Call<List<PetResponse>>, t: Throwable) {
                onError(t.message ?: "Error")
            }
        })
    }



    fun createPet( ownerId: Int, pet: PetRequest, onSuccess: (PetResponse) -> Unit, onError: (String) -> Unit) {
        Log.d("PetRepository", "Attempting to create pet: $pet for owner: $ownerId")
        petService.createPet(ownerId, pet).enqueue(object : Callback<PetResponse> {
            override fun onResponse(call: Call<PetResponse>, response: Response<PetResponse>) {
                if (response.isSuccessful) {
                    val petResponse = response.body()?.let { petResponse ->
                        PetResponse(
                            id = petResponse.id,
                            name = petResponse.name,
                            petOwnerId = petResponse.petOwnerId,
                            breed = petResponse.breed,
                            species = petResponse.species,
                            weight = petResponse.weight,
                            birthdate = petResponse.birthdate,
                            image_url = petResponse.image_url,
                            gender = petResponse.gender
                        )
                    }
                    Log.d("PetRepository", "Pet created successfully: $petResponse")
                    onSuccess(petResponse!!)
                } else {
                    Log.e("PetRepository", "Error creating pet, response unsuccessful. Response: $response")
                    onError("Error")
                }
            }

            override fun onFailure(p0: Call<PetResponse>, t: Throwable) {
                Log.e("PetRepository", "Error creating pet, request failed.", t)
                onError(t.message ?: "Error")
            }
        })
    }

    fun updatePet(petId: Int, pet: PetRequest, onSuccess: (PetResponse) -> Unit, onError: (String) -> Unit) {
        petService.updatePet(petId, pet).enqueue(object : Callback<PetResponse> {
            override fun onResponse(call: Call<PetResponse>, response: Response<PetResponse>) {
                if (response.isSuccessful) {
                    val petResponse = response.body()?.let { petResponse ->
                        PetResponse(
                            id = petResponse.id,
                            name = petResponse.name,
                            petOwnerId = petResponse.petOwnerId,
                            breed = petResponse.breed,
                            species = petResponse.species,
                            weight = petResponse.weight,
                            birthdate = petResponse.birthdate,
                            image_url = petResponse.image_url,
                            gender = petResponse.gender
                        )
                    }
                    onSuccess(petResponse!!)
                } else {
                    onError("Error")
                }
            }

            override fun onFailure(call: Call<PetResponse>, t: Throwable) {
                onError(t.message ?: "Error")
            }
        })
    }

    fun deletePet(petId: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        petService.deletePet(petId).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error")
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                onError(t.message ?: "Error")
            }
        })
    }
}