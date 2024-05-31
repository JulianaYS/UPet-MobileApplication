package pe.edu.upc.upet.feature_appointment.data.remote

import com.google.gson.annotations.SerializedName

typealias AppointmentResponseList = List<AppointmentResponse>
data    class AppointmentResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("diagnosis")
    val diagnosis: String,
    @SerializedName("treatment")
    val treatment: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("petId")
    val petId: Int,
    @SerializedName("veterinarianId")
    val veterinarianId: Int,


    )