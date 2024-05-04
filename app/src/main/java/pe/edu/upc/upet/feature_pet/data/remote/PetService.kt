package pe.edu.upc.upet.feature_pet.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface PetService {
    @GET("pets")
    fun getAll(): Call<PetsResponse>


}