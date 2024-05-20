package pe.edu.upc.upet.feature_pet.domain

import pe.edu.upc.upet.feature_pet.data.remote.GenderEnum
import pe.edu.upc.upet.feature_pet.data.remote.SpeciesEnum
import java.util.Date

typealias Pets = List<Pet>
data class Pet(
    val id: Int,
    val name: String,
    val petOwnerId: Int,
    val breed: String,
    val birthdate: Date,
    val imageUrl: String,
    val weight: Double,
    val specie: SpeciesEnum,
    val gender: GenderEnum
)


