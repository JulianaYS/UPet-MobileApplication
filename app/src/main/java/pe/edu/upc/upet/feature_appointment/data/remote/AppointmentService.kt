package pe.edu.upc.upet.feature_appointment.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentService {
    @GET("appointments")
    fun getAppointments(): Call<List<AppointmentResponse>>

    @GET("appointments/{id}")
    fun getAppointmentById(@Path("id") id: Int): Call<AppointmentResponse>

    @POST("appointments")
    fun createAppointment(@Body appointmentRequest: AppointmentRequest): Call<AppointmentResponse>

    @GET("appointments/pet/{pet_id}")
    fun getAppointmentsByPetId(@Path("pet_id") petId: Int): Call<List<AppointmentResponse>>

    @GET("appointments/veterinarian/{veterinarian_id}")
    fun getAppointmentsByVeterinarianId(@Path("veterinarian_id") veterinarianId: Int): Call<List<AppointmentResponse>>
}