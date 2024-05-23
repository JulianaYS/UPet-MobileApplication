package pe.edu.upc.upet.feature_vetClinics.domain

typealias Veterinaries = List<VeterinaryClinic>
data class VeterinaryClinic(
    val id: Int,
    val name: String,
    val location : String,
    val services: String,
    val office_hours: String,
    val image_url: String
)