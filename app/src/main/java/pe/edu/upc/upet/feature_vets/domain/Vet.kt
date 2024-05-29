package pe.edu.upc.upet.feature_vets.domain

typealias VetList = List<Vet>
data class Vet(
    val id: Int,
    val name: String,
    val clinicId :Int,
    val image_url: String,
    val userId: Int
)

