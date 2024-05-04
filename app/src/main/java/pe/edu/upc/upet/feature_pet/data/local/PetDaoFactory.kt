package pe.edu.upc.upet.feature_pet.data.local

import pe.edu.upc.upet.MyApplication
import pe.edu.upc.upet.core_database.AppDatabase

class PetDaoFactory private constructor(){
    companion object {
        fun getPetDao(): PetDao {
            return AppDatabase.getInstance(MyApplication.getContext()).getPetDao()
        }
    }
}