package pe.edu.upc.upet.feature_vet.data.remote

import pe.edu.upc.upet.core_network.RetrofitFactory

class VeterinaryServiceFactory {
    companion object{
        fun getVeterinaryService(): VeterinaryService{
            return RetrofitFactory.getRetrofit().create(VeterinaryService::class.java)
        }
    }
}