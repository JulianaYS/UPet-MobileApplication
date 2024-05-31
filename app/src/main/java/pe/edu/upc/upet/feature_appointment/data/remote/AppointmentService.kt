package pe.edu.upc.upet.feature_appointment.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentService {
    @POST("appointments/pet/{pet_id}")
    fun createAppointment(@Path("pet_id") pet_Id: Int, @Body appointmentRequest: AppointmentRequest): Call<AppointmentResponse>

    @GET("appointments/pet/{pet_id}")
    fun getByPetId(@Path("pet_id") pet_Id: Int): Call<List<AppointmentResponse>>

}