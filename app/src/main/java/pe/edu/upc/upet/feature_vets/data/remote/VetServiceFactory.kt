package pe.edu.upc.upet.feature_vets.data.remote

import pe.edu.upc.upet.core_network.RetrofitFactory

class VetServiceFactory {
    companion object{
        fun getVetServiceFactory(): VetService {
            return RetrofitFactory.getRetrofit().create(VetService::class.java)
        }
    }
}