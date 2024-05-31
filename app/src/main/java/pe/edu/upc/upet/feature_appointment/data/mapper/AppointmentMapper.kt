package pe.edu.upc.upet.feature_appointment.data.mapper

import pe.edu.upc.upet.feature_appointment.data.remote.AppointmentResponse
import pe.edu.upc.upet.feature_appointment.domain.Appointment

fun AppointmentResponse.toDomainModel(): Appointment{
    return Appointment(
        id = this.id,
        datetime = this.datetime,
        diagnosis = this.diagnosis,
        treatment = this.treatment,
        description = this.description,
        petId = this.petId,
        veterinarianId = this.veterinarianId

    )

}