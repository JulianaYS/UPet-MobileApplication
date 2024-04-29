package pe.edu.upc.upet.core_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.edu.upc.upet.feature_pet.data.local.PetDao
import pe.edu.upc.upet.feature_pet.data.local.PetEntity
import pe.edu.upc.upet.feature_profile.data.local.PetOwnerDao

@Database(entities = [PetEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPetDao(): PetDao
    abstract fun getPetOwnerDao(): PetOwnerDao

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "dbUPet")
                    .allowMainThreadQueries()
                    .build()
            }
            return appDatabase as AppDatabase
        }
    }
}