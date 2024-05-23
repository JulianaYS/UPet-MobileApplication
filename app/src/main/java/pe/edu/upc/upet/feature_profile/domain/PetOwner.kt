package pe.edu.upc.upet.feature_profile.domain

import pe.edu.upc.upet.feature_profile.data.remote.SubscriptionType

typealias PetOwnerList = List<PetOwner>


data class PetOwner(
    val id: Int,
    val name: String,
    val numberPhone: String,
    val location: String,
    val imageUrl: String,
    val subscriptionType: SubscriptionType
)
