package pe.edu.upc.upet.feature_pet.data.mapper

import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import pe.edu.upc.upet.feature_pet.domain.Pet

fun PetResponse.toDomainModel(): Pet {
    return Pet(
        id = this.id,
        name = this.name,
        age = this.birthdate,
        breed = this.breed,
        image_url = this.image_url,
        weight = this.weight,
        specie = this.species,
        gender = this.gender,
        )
}