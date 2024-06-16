package pe.edu.upc.upet.feature_notification.data.remote

import pe.edu.upc.upet.feature_pet.data.remote.PetRequest
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationService {
    @POST("notifications")
    fun createNotification(@Body notificationRequest: NotificationRequest): Call<NotificationResponse>
}
