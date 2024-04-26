package pe.edu.upc.upet.feature_pet.data.remote

class PetServiceFactory private constructor() {
    companion object {
        fun getPetService(): PetService {
            return RetrofitFactory.getRetrofit().create(PetService::class.java)
        }
    }
}