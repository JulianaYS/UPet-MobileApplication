package pe.edu.upc.upet.feature_profile.data.remote

import pe.edu.upc.upet.feature_auth.data.remote.SignInResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface PetOwnerService {
    companion object {
        const val BASE_ENDPOINT = "petOwners/"
    }

    @GET("petOwners/")
    fun getAll(): Call<PetOwnerResponseList>

    @GET("petOwners/users/{user_id}")
    fun getByUserId(@Path("user_id") userId: Int): Call<PetOwnerResponse>

    @GET("petOwners/{id}")
    fun getById(@Path("id") id: Int): Call<PetOwnerResponse>

    @POST("petOwners/{user_id}")
    fun createPetOwner(
        @Path("user_id") userId: Int,
        @Body petOwnerData: PetOwnerRequest
    ): Call<SignInResponse>




}