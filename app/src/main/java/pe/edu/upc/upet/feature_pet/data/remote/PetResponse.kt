package pe.edu.upc.upet.feature_pet.data.remote

import java.util.Date

data class PetResponse(
    val id: Int,
    val name: String,
    val petOwnerId: Int,
    val breed: String,
    val species: SpeciesEnum,
    val weight: Double,
    val birthdate: Date,
    val imageUrl: String,
    val gender: GenderEnum
)

enum class GenderEnum {
    Male,
    Female
}

enum class SpeciesEnum {
    Dog,
    Cat,
    Bird,
    Fish,
    Reptile,
    Rodent,
    Rabbit,
    Other
}
