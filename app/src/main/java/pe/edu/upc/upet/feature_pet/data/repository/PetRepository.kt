package pe.edu.upc.upet.feature_pet.data.repository

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
                            age = petResponse.age,
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


}