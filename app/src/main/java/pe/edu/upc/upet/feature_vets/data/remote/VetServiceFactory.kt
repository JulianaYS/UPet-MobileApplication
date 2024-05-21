package pe.edu.upc.upet.feature_vetClinics.data.remote

import pe.edu.upc.upet.core_network.RetrofitFactory
import pe.edu.upc.upet.feature_vets.data.remote.VetService

class VetServiceFactory {
    companion object{
        fun getVetServiceFactory(): VetService {
            return RetrofitFactory.getRetrofit().create(VetService::class.java)
        }
    }
}