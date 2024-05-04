package pe.edu.upc.upet.feature_vet.data.repository

import android.util.Log
import pe.edu.upc.upet.feature_vet.data.local.VeterinaryDao
import pe.edu.upc.upet.feature_vet.data.local.VeterinaryDaoFactory
import pe.edu.upc.upet.feature_vet.data.remote.VeterinariesResponse
import pe.edu.upc.upet.feature_vet.data.remote.VeterinaryService
import pe.edu.upc.upet.feature_vet.data.remote.VeterinaryServiceFactory
import pe.edu.upc.upet.feature_vet.domain.Veterinaries
import pe.edu.upc.upet.feature_vet.domain.Veterinary
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VeterinaryRepository {
    private val veterinaryService: VeterinaryService = VeterinaryServiceFactory.getVeterinaryService()
    private val veterinaryDao: VeterinaryDao = VeterinaryDaoFactory.getVeterinaryDao()

    fun getAll(callback: (Veterinaries)-> Unit){
        val getAll = veterinaryService.getAll()
        getAll.enqueue(object : Callback<VeterinariesResponse>{
            override fun onResponse(
                call: Call<VeterinariesResponse>,
                response: Response<VeterinariesResponse>
            ){
                if(response.isSuccessful){
                    val veterinariesResponse = response.body() as VeterinariesResponse
                    var veterinaries: Veterinaries = arrayListOf()
                    for(veterinaryResponse in veterinariesResponse ){
                        veterinaries = veterinaries + Veterinary(
                            veterinaryResponse.name,
                            veterinaryResponse.location,
                            veterinaryResponse.services,
                            veterinaryResponse.hours
                        )
                    }
                    callback(veterinaries)
                }
            }
            override fun onFailure(call: Call<VeterinariesResponse>, t:Throwable){
                t.message?.let {
                    Log.d("VeterinaryRepository", it)
                }
            }
        }



        )
    }
}
