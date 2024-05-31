package pe.edu.upc.upet.feature_appointment.data.repository

import android.util.Log
import pe.edu.upc.upet.feature_appointment.data.remote.AppointmentRequest
import pe.edu.upc.upet.feature_appointment.data.remote.AppointmentResponse
import pe.edu.upc.upet.feature_appointment.data.remote.AppointmentService
import pe.edu.upc.upet.feature_appointment.data.remote.AppointmentServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentRepository(
    private val appointmentService: AppointmentService = AppointmentServiceFactory.getAppointmentService()
) {
    fun createAppointment(petId: Int, appointment: AppointmentRequest, onSuccess: (AppointmentResponse) -> Unit, onError: (String) -> Unit){
        Log.d("AppointmentRepository", "Attempting to create appointment: $appointment for pet: $petId")
        appointmentService.createAppointment(petId, appointment).enqueue(object : Callback<AppointmentResponse> {
            override fun onResponse(call: Call<AppointmentResponse>, response: Response<AppointmentResponse>) {
                if (response.isSuccessful) {
                    val appointmentResponse = response.body()?.let { appointmentResponse ->
                        AppointmentResponse(
                            id = appointmentResponse.id,
                            date = appointmentResponse.date,
                            time = appointmentResponse.time,
                            description = appointmentResponse.description,
                            vetName = appointmentResponse.vetName,
                            petId = appointmentResponse.petId,
                            vetClinics = appointmentResponse.vetClinics
                        )
                    }
                    Log.d("AppointmentRepository", "Appointment created successfully: $appointmentResponse")
                    onSuccess(appointmentResponse!!)
                } else {
                    Log.e("AppointmentRepository", "Error creating appointment, response unsuccessful. Response: $response")
                    onError("Error")
                }
            }

            override fun onFailure(call: Call<AppointmentResponse>, t: Throwable) {
                Log.e("AppointmentRepository", "Error creating appointment, request failed.", t)
                onError(t.message ?: "Error")
            }
        })
    }
}
