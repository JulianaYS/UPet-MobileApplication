package pe.edu.upc.upet.feature_vetClinics.data.remote

typealias  VeterinaryClinicResponseList = List<VeterinaryClinicResponse>
data class VeterinaryClinicResponse(
    val id: Int,
    val name: String,
    val location : String,
    val services: String,
    val office_hours: String,
    val image_url: String
)