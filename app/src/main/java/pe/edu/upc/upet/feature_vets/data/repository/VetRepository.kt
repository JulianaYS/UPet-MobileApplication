package pe.edu.upc.upet.feature_vets.data.repository


import android.util.Log
import pe.edu.upc.upet.feature_auth.data.remote.SignInResponse
import pe.edu.upc.upet.feature_vets.data.mapper.toDomainModel
import pe.edu.upc.upet.feature_vets.data.remote.VetResponse
import pe.edu.upc.upet.feature_vets.data.remote.VetResponseList
import pe.edu.upc.upet.feature_vets.data.remote.VetService
import pe.edu.upc.upet.feature_vets.domain.Vet
import pe.edu.upc.upet.feature_vets.domain.VetList
import pe.edu.upc.upet.feature_vetClinics.data.remote.VetRequest
import pe.edu.upc.upet.feature_vetClinics.data.remote.VetServiceFactory
import pe.edu.upc.upet.utils.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VetRepository (
    private val vetService: VetService = VetServiceFactory.getVetServiceFactory())
{
    fun getVets(callback: (VetList) -> Unit){
        vetService.getVets().enqueue(object : Callback<VetResponseList> {
            override fun onResponse(
                call: Call<VetResponseList>,
                response: Response<VetResponseList>
            ) {
                if (response.isSuccessful){
                    val vets = response.body()?.map { it.toDomainModel() }?: emptyList()
                    callback(vets)
                }else{
                    Log.e("VetRepository", "Failed to get vets: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<VetResponseList>, t: Throwable) {
                Log.e("VetRepository", "Failed to get vets", t)
            }
        })
        }


    fun getVetById(vetId: Int, callback: (Vet?) -> Unit){
        vetService.getVetById(vetId).enqueue(object : Callback<VetResponse>{
            override fun onResponse(
                call: Call<VetResponse>,
                response: Response<VetResponse>
            ) {
                if (response.isSuccessful){
                    val vet = response.body()?.toDomainModel()
                    callback(vet)
                }else{
                    callback(null)
                }
            }

            override fun onFailure(call: Call<VetResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun createVet(userId: Int, vetData: VetRequest, callback: (Boolean) -> Unit){
        vetService.createVet(userId, vetData).enqueue(object : Callback<SignInResponse>{
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                TokenManager.clearToken()
                if (response.isSuccessful){
                    val access  = response.body()?.access_token
                    callback(true)
                }else{
                    Log.e("CreateVet", "Unsuccessful response: ${response.code()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun getVetsByUserId(userId: Int, callback: (Vet?) -> Unit){
        vetService.getVetsByUserId(userId).enqueue(object : Callback<VetResponse>{
            override fun onResponse(
                call: Call<VetResponse>,
                response: Response<VetResponse>
            ) {
                if (response.isSuccessful){
                    val vet = response.body()?.toDomainModel()
                    callback(vet)
                }else{
                    callback(null)
                }
            }

            override fun onFailure(call: Call<VetResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

}
