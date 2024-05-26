package pe.edu.upc.upet.feature_pet.data.remote

data class PetResponse(
    val id: Int,
    val name: String,
    val petOwnerId: Int,
    val breed: String,
    val species: String,
    val weight: Float,
    val birthdate: String,
    val image_url: String,
    val gender: GenderEnum
)

enum class GenderEnum {
    Male,
    Female
}
