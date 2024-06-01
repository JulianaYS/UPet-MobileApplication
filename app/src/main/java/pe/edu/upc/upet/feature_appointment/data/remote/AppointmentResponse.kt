package pe.edu.upc.upet.feature_appointment.data.remote

data class AppointmentResponse(
    val id: Int,
    val datetime: String,
    val diagnosis: String,
    val treatment: String,
    val description: String,
    val petId: Int,
    val veterinarianId: Int
)
