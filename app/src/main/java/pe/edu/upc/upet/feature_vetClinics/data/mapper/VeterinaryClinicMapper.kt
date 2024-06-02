package pe.edu.upc.upet.feature_vetClinics.data.mapper


import pe.edu.upc.upet.feature_vetClinics.data.remote.VeterinaryClinicResponse
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic


fun VeterinaryClinicResponse.toDomainModel(): VeterinaryClinic {
    return VeterinaryClinic(
        id = this.id,
        name = this.name,
        location = this.location,
        services = this.services,
        image_url = this.image_url,
        description = this.description,
        office_hours_start = this.office_hours_start,
        office_hours_end = this.office_hours_end

    )
}
