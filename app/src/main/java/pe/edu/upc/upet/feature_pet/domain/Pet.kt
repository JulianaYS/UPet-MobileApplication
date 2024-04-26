package pe.edu.upc.upet.feature_pet.domain

typealias Pets = List<Pet>
data class Pet(
    val id: Int,
    val name: String,
    val age: Int,
    val breed: String,
)
