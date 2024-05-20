package pe.edu.upc.upet.core_database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.edu.upc.upet.feature_profile.data.local.PetOwnerDao
import pe.edu.upc.upet.feature_vet.data.local.VeterinaryDao


abstract class AppDatabase : RoomDatabase() {

    abstract fun getVeterinaryDao(): VeterinaryDao
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