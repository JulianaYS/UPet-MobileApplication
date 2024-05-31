package pe.edu.upc.upet.feature_appointment.data.repository

import android.util.Log
import pe.edu.upc.upet.feature_appointment.data.mapper.toDomainModel
import pe.edu.upc.upet.feature_appointment.data.remote.AppointmentResponseList
import pe.edu.upc.upet.feature_appointment.data.remote.AppointmentService
import pe.edu.upc.upet.feature_appointment.data.remote.AppointmentServiceFactory
import pe.edu.upc.upet.feature_appointment.domain.Appointments
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppointmentRepository(private val appointmentService: AppointmentService = AppointmentServiceFactory.getAppointmentService()) {

    fun getAllAppointment(callback: (Appointments)->Unit){
        appointmentService.getAll().enqueue(object: Callback<AppointmentResponseList>{
            override fun onResponse(
                call: Call<AppointmentResponseList>,
                response: Response<AppointmentResponseList>){
                if(response.isSuccessful){
                    val appointments = response.body()?.map{ it.toDomainModel() }?: emptyList()
                    callback(appointments)
                }else{
                    Log.e("AppointmentsRepository", "Failed to get appointments: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<AppointmentResponseList>, t:Throwable){
                Log.e("AppointmentsRepository", "Failed to get appointments", t)
            }

        })
    }


    fun getAppointmentsByVetId(vetId: Int, callback: (Appointments)->Unit){
        appointmentService.getAppointmentByVetId(vetId).enqueue(object: Callback<AppointmentResponseList>{
            override fun onResponse(
                call: Call<AppointmentResponseList>,
                response: Response<AppointmentResponseList>){
                if(response.isSuccessful){
                    val  appointments = response.body()?.map{it.toDomainModel()}?: emptyList()
                    callback(appointments)
                }else{
                    Log.e("VeterinaryClinicRepository", "Failed to get veterinary clinics: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<AppointmentResponseList>,t: Throwable){
                Log.e("VeterinaryClinicRepository", "Failed to get veterinary clinics", t)
            }
        } )
    }


}
