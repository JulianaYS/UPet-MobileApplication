package pe.edu.upc.upet.feature_vets.data.remote

typealias  VetResponseList = List<VetResponse>
data class VetResponse(
    val id: Int,
    val name: String,
    val clinicId :Int,
    val image_url: String,
    val userId: Int
)
