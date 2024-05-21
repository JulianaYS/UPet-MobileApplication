package pe.edu.upc.upet.feature_vetClinics.domain


data class VeterinaryClinics(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val address: String,
    val contactNumber: String,
    val operatingHours: String,
)

val veterinaryClinics = listOf(
    VeterinaryClinics(
        id = 1,
        name = "PetCenter",
        imageUrl = "https://static.vecteezy.com/system/resources/previews/006/099/883/original/illustration-of-the-logo-of-the-veterinary-clinic-vector.jpg",
        address = "123 Pet Street",
        contactNumber = "(123) 456-7890",
        operatingHours = "9:00 AM - 5:00 PM"
    ),
    VeterinaryClinics(
        id = 2,
        name = "PalacePet",
        imageUrl = "https://img.freepik.com/vector-premium/logotipo-clinica-veterinaria-animales-mascotas-logotipo-cherty-salud-perros-gatos_306040-4910.jpg",
        address = "456 Animal Avenue",
        contactNumber = "(098) 765-4321",
        operatingHours = "10:00 AM - 6:00 PM"
    ),
    VeterinaryClinics(
        id = 3,
        name = "PalacePet",
        imageUrl = "https://img.freepik.com/vector-premium/logotipo-clinica-veterinaria-animales-mascotas-logotipo-cherty-salud-perros-gatos_306040-4910.jpg",
        address = "456 Animal Avenue",
        contactNumber = "(098) 765-4321",
        operatingHours = "10:00 AM - 6:00 PM"
    ),
    VeterinaryClinics(
        id = 4,
        name = "PetCenter",
        imageUrl = "https://static.vecteezy.com/system/resources/previews/006/099/883/original/illustration-of-the-logo-of-the-veterinary-clinic-vector.jpg",
        address = "123 Pet Street",
        contactNumber = "(123) 456-7890",
        operatingHours = "9:00 AM - 5:00 PM"
    ),
    // Add more clinics as needed
)