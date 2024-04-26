package pe.edu.upc.upet.feature_pet.data.remote

typealias PetsResponse = List<PetResponse>
data class PetResponse(
    val id: Int,
    val name: String,
    val age: Int,
    val breed: String
)

