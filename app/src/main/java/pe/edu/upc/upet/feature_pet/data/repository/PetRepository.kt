package pe.edu.upc.upet.feature_pet.data.repository

import android.util.Log
import pe.edu.upc.upet.feature_pet.data.local.PetDao
import pe.edu.upc.upet.feature_pet.data.local.PetDaoFactory
import pe.edu.upc.upet.feature_pet.data.remote.PetService
import pe.edu.upc.upet.feature_pet.data.remote.PetServiceFactory
import pe.edu.upc.upet.feature_pet.data.remote.PetsResponse
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.feature_pet.domain.Pets
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PetRepository(
    private val petService: PetService = PetServiceFactory.getPetService(),
    private val petDao: PetDao = PetDaoFactory.getPetDao()
) {

    fun getAll(callback: (Pets) -> Unit) {
        val getAll = petService.getAll()

        getAll.enqueue(object : Callback<PetsResponse> {
            override fun onResponse(
                call: Call<PetsResponse>,
                response: Response<PetsResponse>
            ) {
                if (response.isSuccessful) {
                    val petsResponse = response.body() as PetsResponse
                    var pets: Pets = arrayListOf()
                    for (petResponse in petsResponse) {
                        pets = pets + Pet(
                            petResponse.id,
                            petResponse.name,
                            petResponse.age,
                            petResponse.breed,
                        )
                    }
                    callback(pets)
                }
            }

            override fun onFailure(call: Call<PetsResponse>, t: Throwable) {
                t.message?.let {
                    Log.d("PetRepository", it)
                }
            }
        })
    }
}