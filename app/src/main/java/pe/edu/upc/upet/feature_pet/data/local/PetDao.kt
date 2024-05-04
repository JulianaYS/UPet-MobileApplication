package pe.edu.upc.upet.feature_pet.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PetDao {
    @Query("select * from pets where id=:id")
    fun fetchById(id: Int): PetEntity?

    @Insert
    fun insert(restaurantEntity: PetEntity)

    @Delete
    fun delete(restaurantEntity: PetEntity)

}