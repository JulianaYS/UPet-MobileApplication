package pe.edu.upc.upet.feature_pet.data.remote

import java.util.Date

data class PetRequest(
    val name: String,
    val petOwnerId: Int,
    val breed: String,
    val species: SpeciesEnum,
    val weight: Double,
    val birthdate: Date,
    val imageUrl: String, // Aquí se especifica que la clave en el JSON es "image_url"
    val gender: GenderEnum
)

