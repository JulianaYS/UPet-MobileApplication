package pe.edu.upc.upet.feature_profile.data.mapper

import pe.edu.upc.upet.feature_profile.data.remote.PetOwnerResponse
import pe.edu.upc.upet.feature_profile.data.remote.PetOwnerResponseList
import pe.edu.upc.upet.feature_profile.domain.PetOwner
import pe.edu.upc.upet.feature_profile.domain.PetOwnerList


fun PetOwnerResponse.toDomainModel(): PetOwner {
    return PetOwner(
        id = this.id,
        name = this.name,
        numberPhone = this.numberPhone,
        imageUrl = this.image_url,
        subscriptionType = this.subscriptionType
    )
}
