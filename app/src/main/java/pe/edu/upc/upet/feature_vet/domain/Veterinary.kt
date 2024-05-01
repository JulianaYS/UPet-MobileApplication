package pe.edu.upc.upet.feature_vet.domain

typealias Veterinaries = List<Veterinary>
data class Veterinary(
    val name: String,
    val location : String,
    val services: String,
    val hours: String
)