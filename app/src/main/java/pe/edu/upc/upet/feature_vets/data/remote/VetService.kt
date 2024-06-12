package pe.edu.upc.upet.feature_vets.data.remote

import pe.edu.upc.upet.feature_auth.data.remote.SignInResponse
import pe.edu.upc.upet.feature_reviews.data.remote.ReviewResponse
import pe.edu.upc.upet.feature_reviews.data.remote.VetResponseWithReviews
import pe.edu.upc.upet.feature_vetClinics.data.remote.VetRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VetService {

    @POST("veterinarians/{user_id}")
    fun createVet(
        @Path("user_id") userId: Int,
        @Body vetRequest: VetRequest): Call<SignInResponse>

    @GET("veterinarians")
    fun getVets(): Call<VetResponseList>

    @GET("veterinarians/{vet_id}")
    fun getVetById(@Path("vet_id") vetId: Int): Call<VetResponse>

    @GET("veterinarians/users/{user_id}")
    fun getVetsByUserId(@Path("user_id") userId: Int): Call<VetResponse>

    @GET("veterinarians/vets/{clinic_id}")
    fun getVetsByClinicId(@Path("clinic_id") clinicId: Int): Call<List<VetResponse>>

    @GET("veterinarians/reviews/{vet_id}")
    fun getVetReviews(@Path("vet_id") vetId: Int): Call<VetResponseWithReviews>

    @POST("veterinarians/{vet_id}")
    fun updateVet(
        @Path("vet_id") vetId: Int,
        @Body vetRequest: VetUpdateRequest): Call<VetResponse>
}