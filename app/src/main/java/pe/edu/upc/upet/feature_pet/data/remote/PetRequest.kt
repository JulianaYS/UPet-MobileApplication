package pe.edu.upc.upet.feature_pet.data.remote

data class PetRequest(
    val name: String,
    val petOwnerId: Int,
    val breed: String,
    val species: String,
    val weight: Float,
    val age: Int,
    val image_url: String, // Aquí se especifica que la clave en el JSON es "image_url"
    val gender: GenderEnum
)

