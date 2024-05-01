package pe.edu.upc.upet.feature_vet.data.local

import androidx.room.Entity

@Entity(tableName= "veterinaries")
data class VeterinaryEntity(
    val id: Int,
    val name: String,
    val location: String,
    val services: String,
    val hours: String

)
