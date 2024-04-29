package pe.edu.upc.upet.feature_profile.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface PetOwnerDao {
    @Query("select * from petowners where id=:id")
    fun fetchById(id: Int): PetOwnerEntity?

    @Insert
    fun insert(petOwnerEntity: PetOwnerEntity)

    @Delete
    fun delete(petOwnerEntity: PetOwnerEntity)
}