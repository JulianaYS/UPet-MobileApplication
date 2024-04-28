package pe.edu.upc.upet.feature_profile.data.remote


typealias PetOwnersResponse = List<PetOwnerResponse>
data class PetOwnerResponse (
    val id: Int,
    val subscription: String
)
