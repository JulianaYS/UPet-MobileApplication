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
    Pet(1, "Fido", 5,"Bulldog",  "https://estaticos-cdn.prensaiberica.es/clip/823f515c-8143-4044-8f13-85ea1ef58f3a_16-9-discover-aspect-ratio_default_0.jpg"),
    Pet(2, "Rex", 3, "Labrador",  "https://humanidades.com/wp-content/uploads/2017/02/perro-3-e1561679226953.jpg"),

    )


