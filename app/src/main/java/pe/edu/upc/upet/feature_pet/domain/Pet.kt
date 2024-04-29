package pe.edu.upc.upet.feature_pet.domain

typealias Pets = List<Pet>
data class Pet(
    val id: Int,
    val name: String,
    val age: Int,
    val breed: String,
    val imageUrl: String
)


val pets = listOf(
    Pet(1, "Fido", 5,"Bulldog",  "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQymJQQtLOL5I7deClYWZfp7txWwo1zxoLnQxaDsQ26fw&s"),
    Pet(2, "Rex", 3, "Labrador",  "https://humanidades.com/wp-content/uploads/2017/02/perro-3-e1561679226953.jpg"),
    Pet(3, "Rex", 3, "Labrador",  "https://humanidades.com/wp-content/uploads/2017/02/perro-3-e1561679226953.jpg"),
    Pet(4, "Rex", 3, "Labrador",  "https://humanidades.com/wp-content/uploads/2017/02/perro-3-e1561679226953.jpg"),

    )


