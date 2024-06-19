package pe.edu.upc.upet.feature_medycalHistory.data.remote

import pe.edu.upc.upet.core_network.RetrofitFactory
import pe.edu.upc.upet.feature_notification.data.remote.NotificationService

class MedicalHistoryServiceFactory private constructor(){
    companion object {
        fun getMedicalHistoryService(): MedicalHistoryService {
            return RetrofitFactory.getRetrofit().create(MedicalHistoryService::class.java)
        }
    }
}

