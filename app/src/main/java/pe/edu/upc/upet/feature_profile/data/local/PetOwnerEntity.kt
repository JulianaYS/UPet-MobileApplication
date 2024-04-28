package pe.edu.upc.upet.feature_profile.data.local

import androidx.room.Entity

@Entity(tableName = "PetOwners")
data class PetOwnerEntity (
    val id: Int,
    val userId: Int,
    val subscription: String

)