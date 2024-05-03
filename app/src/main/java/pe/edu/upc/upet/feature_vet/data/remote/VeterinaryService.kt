package pe.edu.upc.upet.feature_vet.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface VeterinaryService {
    @GET("veterinaries")
    fun getAll(): Call<VeterinariesResponse>
}