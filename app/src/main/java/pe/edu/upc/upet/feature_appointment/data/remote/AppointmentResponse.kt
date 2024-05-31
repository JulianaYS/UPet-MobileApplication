package pe.edu.upc.upet.feature_appointment.data.remote

data class AppointmentResponse(
    val id: Int,
    val date: String,
    val time: String,
    val description: String,
    val vetName: String,
    val petId: Int,
    val vetClinics: Int
)
