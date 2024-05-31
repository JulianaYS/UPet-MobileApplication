package pe.edu.upc.upet.feature_appointment.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentService {
    @GET("appointments")
    fun getAll(): Call<AppointmentResponseList>

    @POST("appointments")
    fun createAppointment(
        @Body appointmentRequest: AppointmentRequest
    ): Call<AppointmentRequest>

    @GET("appointments/pet/{pet_id}")
    fun getAppointmentByPetId(
        @Path("pet_id") petId: Int
    ): Call<AppointmentResponseList>
    @GET("appointments/veterinarian/{veterinarian_id}")
    fun getAppointmentByVetId(
        @Path("veterinarian_id") veterinarian_id: Int
    ): Call<AppointmentResponseList>

}