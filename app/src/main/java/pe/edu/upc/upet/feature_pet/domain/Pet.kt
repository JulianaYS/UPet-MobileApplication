package pe.edu.upc.upet.feature_pet.domain

import androidx.compose.ui.text.font.FontWeight

typealias Pets = List<Pet>
data class Pet(
    val id: Int,
    val name: String,
    val age: Int,
    val breed: String,
    val imageUrl: String,
    val weight: Int,
    val specie: String
)

val pets = listOf(
    Pet(0, "Fido", 5, "Bulldog", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQymJQQtLOL5I7deClYWZfp7txWwo1zxoLnQxaDsQ26fw&s", 5, "Dog"),
    Pet(1, "Rex", 3, "Labrador", "https://humanidades.com/wp-content/uploads/2017/02/perro-3-e1561679226953.jpg", 20, "Dog"),
    Pet(2, "Lau", 3, "Labrador", "https://humanidades.com/wp-content/uploads/2017/02/perro-3-e1561679226953.jpg", 15, "Dog"),
    Pet(3, "Toto", 3, "Labrador", "https://humanidades.com/wp-content/uploads/2017/02/perro-3-e1561679226953.jpg", 18, "Dog")
)

