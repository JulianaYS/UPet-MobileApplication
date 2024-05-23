package pe.edu.upc.upet.feature_vetClinics.data.mapper


import pe.edu.upc.upet.feature_vetClinics.data.remote.VeterinaryClinicResponse
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic


fun VeterinaryClinicResponse.toDomainModel(): VeterinaryClinic {
    return VeterinaryClinic(
        id = this.id,
        name = this.name,
        location = this.location,
        services = this.services,
        office_hours = this.office_hours,
        image_url = this.image_url
    )
}
