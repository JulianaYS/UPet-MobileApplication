package pe.edu.upc.upet.feature_profile.data.repository

import android.util.Log
import pe.edu.upc.upet.feature_profile.data.local.PetOwnerDao
import pe.edu.upc.upet.feature_profile.data.local.PetOwnerDaoFactory
import pe.edu.upc.upet.feature_profile.data.remote.PetOwnerResponse
import pe.edu.upc.upet.feature_profile.data.remote.PetOwnerService
import pe.edu.upc.upet.feature_profile.data.remote.PetOwnerServiceFactory
import pe.edu.upc.upet.feature_profile.data.remote.PetOwnersResponse
import pe.edu.upc.upet.feature_profile.domain.PetOwner
import pe.edu.upc.upet.feature_profile.domain.PetOwners
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PetOwnerRepository(
    private val petOwnerService: PetOwnerService = PetOwnerServiceFactory.getPetOwnerService(),
    private val petOwnerDao: PetOwnerDao = PetOwnerDaoFactory.getPetOwnerDao()
) {
    fun getAll(callback: (PetOwners) -> Unit){
        val getAll = petOwnerService.getAll()
        getAll.enqueue(object : Callback<PetOwnersResponse>{
            override fun onResponse(
                call: Call<PetOwnersResponse>,
                response: Response<PetOwnersResponse>
            ) {
                if(response.isSuccessful){
                    val petOwnersResponse = response.body() as PetOwnersResponse
                    var petOwners: PetOwners = arrayListOf()
                    for(petOwnerResponse in petOwnersResponse){
                        petOwners = petOwners + PetOwner(
                            petOwnerResponse.subscription
                        )
                    }
                    callback(petOwners)
                }
            }
            override fun onFailure(call: Call<PetOwnersResponse>,t: Throwable){
                t.message?.let {
                    Log.d("PetOwnerRepository", it)
                }
            }
        })
    }

}