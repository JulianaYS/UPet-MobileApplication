package pe.edu.upc.upet.feature_profile.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface PetOwnerService {
    @GET("petOwners")
    fun getAll(): Call<PetOwnersResponse>
}