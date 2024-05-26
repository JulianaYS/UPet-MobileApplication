package pe.edu.upc.upet.feature_pet.domain

import androidx.compose.ui.text.font.FontWeight

typealias Pets = List<Pet>
data class Pet(
    val id: Int,
    val name: String,
    val age: Int,
    val breed: String,
    val image_url: String,
    val weight: Int,
    val specie: String,
    val gender: String
)
