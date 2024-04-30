package pe.edu.upc.upet.feature_appointment.domain

import pe.edu.upc.upet.feature_vet.domain.VeterinaryClinics

data class Appointment(
    val date: String,
    val time: String,
    val vetName: String,
    val veterinaryClinic: VeterinaryClinics
)

val appointments = listOf(
    Appointment(
        date = "2023-05-01",
        time = "10:00",
        vetName = "Dr. Smith",
        veterinaryClinic = VeterinaryClinics(
            id = 1,
            name = "PetCenter",
            imageUrl = "https://static.vecteezy.com/system/resources/previews/006/099/883/original/illustration-of-the-logo-of-the-veterinary-clinic-vector.jpg",
            address = "123 Pet Street",
            contactNumber = "(123) 456-7890",
            operatingHours = "9:00 AM - 5:00 PM"
        )
    ),
    Appointment(
        date = "2023-05-02",
        time = "14:00",
        vetName = "Dr. Johnson",
        veterinaryClinic =
        VeterinaryClinics(
            id = 2,
            name = "PalacePet",
            imageUrl = "https://img.freepik.com/vector-premium/logotipo-clinica-veterinaria-animales-mascotas-logotipo-cherty-salud-perros-gatos_306040-4910.jpg",
            address = "456 Animal Avenue",
            contactNumber = "(098) 765-4321",
            operatingHours = "10:00 AM - 6:00 PM"
        )
    )
)