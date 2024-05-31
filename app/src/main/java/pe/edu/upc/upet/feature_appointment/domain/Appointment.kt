package pe.edu.upc.upet.feature_appointment.domain

import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinics


typealias Appointments = List<Appointment>
data class Appointment(
    val id: Int,
    val datetime: String,
    val diagnosis: String,
    val treatment: String,
    val description: String,
    val petId: Int,
    val veterinarianId: Int
)