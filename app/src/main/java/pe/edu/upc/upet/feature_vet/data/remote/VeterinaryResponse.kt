package pe.edu.upc.upet.feature_vet.data.remote

typealias  VeterinariesResponse = List<VeterinaryResponse>
data class VeterinaryResponse(
    val id: Int,
    val name: String,
    val location : String,
    val services: String,
    val hours: String
)