package pe.edu.upc.upet.feature_appointment.data.repository

import pe.edu.upc.upet.feature_appointment.data.mapper.toDomainModel
import pe.edu.upc.upet.feature_appointment.data.remote.AppointmentRequest
import pe.edu.upc.upet.feature_appointment.data.remote.AppointmentResponse
import pe.edu.upc.upet.feature_appointment.data.remote.AppointmentService
import pe.edu.upc.upet.feature_appointment.data.remote.AppointmentServiceFactory
import pe.edu.upc.upet.feature_appointment.domain.Appointment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentRepository(
    private val appointmentService: AppointmentService = AppointmentServiceFactory.getAppointmentService()
) {
    fun createAppointment(appointment: AppointmentRequest, callback: (Boolean) -> Unit){
         appointmentService.createAppointment(appointment).enqueue(object : Callback<AppointmentResponse> {
            override fun onResponse(call: Call<AppointmentResponse>, response: Response<AppointmentResponse>) {
                if (response.isSuccessful) {
                    val appointmentResponse = response.body()?.toDomainModel()
                    callback(true)
                } else {
                    callback(false)
                }
            }

            override fun onFailure(call: Call<AppointmentResponse>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun getAppointmentsByPetId(petId: Int, callback: (List<Appointment>) -> Unit) {
        appointmentService.getAppointmentsByPetId(petId)
            .enqueue(object : Callback<List<AppointmentResponse>> {
                override fun onResponse(
                    call: Call<List<AppointmentResponse>>,
                    response: Response<List<AppointmentResponse>>
                ) {
                    if (response.isSuccessful) {
                        val appointments =
                            response.body()?.map { it.toDomainModel() } ?: emptyList()
                        callback(appointments)
                    } else {
                        callback(emptyList())
                    }
                }

                override fun onFailure(call: Call<List<AppointmentResponse>>, t: Throwable) {
                    callback(emptyList())
                }
            })
    }

    fun getAppointmentById(id: Int, callback: (Appointment?) -> Unit) {
        appointmentService.getAppointmentById(id)
            .enqueue(object : Callback<AppointmentResponse> {
                override fun onResponse(
                    call: Call<AppointmentResponse>,
                    response: Response<AppointmentResponse>
                ) {
                    if (response.isSuccessful) {
                        val appointment = response.body()?.toDomainModel()
                        callback(appointment)
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<AppointmentResponse>, t: Throwable) {
                    callback(null)
                }
            })
    }

    fun getAppointmentsByVeterinarianId(veterinarianId: Int, callback: (List<Appointment>) -> Unit) {
        appointmentService.getAppointmentsByVeterinarianId(veterinarianId)
            .enqueue(object : Callback<List<AppointmentResponse>> {
                override fun onResponse(
                    call: Call<List<AppointmentResponse>>,
                    response: Response<List<AppointmentResponse>>
                ) {
                    if (response.isSuccessful) {
                        val appointments =
                            response.body()?.map { it.toDomainModel() } ?: emptyList()
                        callback(appointments)
                    } else {
                        callback(emptyList())
                    }
                }

                override fun onFailure(call: Call<List<AppointmentResponse>>, t: Throwable) {
                    callback(emptyList())
                }
            })
    }

    fun getAppointments(callback: (List<Appointment>) -> Unit) {
        appointmentService.getAppointments()
            .enqueue(object : Callback<List<AppointmentResponse>> {
            override fun onResponse(
                call: Call<List<AppointmentResponse>>,
                response: Response<List<AppointmentResponse>>
            ) {
                if (response.isSuccessful) {
                    val appointments = response.body()?.map { it.toDomainModel() } ?: emptyList()
                    callback(appointments)
                } else {
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<AppointmentResponse>>, t: Throwable) {
                callback(emptyList())
            }
        })
    }
}
