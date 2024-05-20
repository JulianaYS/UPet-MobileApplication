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
                    onSuccess(response.body() ?: emptyList())
                } else {
                    onError("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<PetResponse>>, t: Throwable) {
                onError(t.message ?: "Unknown Error")
            }
        })
    }

    fun createPet(petRequest: PetRequest, onSuccess: (PetResponse) -> Unit, onError: (String) -> Unit){
        petService.createPet(petRequest).enqueue(object : Callback<PetResponse> {
            override fun onResponse(call: Call<PetResponse>, response: Response<PetResponse>) {
                if (response.isSuccessful) {
                    onSuccess(response.body()!!)
                } else {
                    onError("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<PetResponse>, t: Throwable) {
                onError(t.message ?: "Unknown Error")
            }
        })
    }


}