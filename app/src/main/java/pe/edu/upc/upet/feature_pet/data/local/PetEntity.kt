package pe.edu.upc.upet.feature_pet.data.local

import androidx.room.Entity

@Entity(tableName = "pets")
data class PetEntity(
    val id: Int,
)
