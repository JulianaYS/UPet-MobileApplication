package pe.edu.upc.upet.feature_vet.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VeterinaryDao {
    @Query("select * from veterinaries where id=:id")
    fun fetchById(id: Int):VeterinaryEntity?

    @Insert
    fun insert(veterinaryEntity: VeterinaryEntity)

    @Delete
    fun delete(veterinaryEntity: VeterinaryEntity)
}