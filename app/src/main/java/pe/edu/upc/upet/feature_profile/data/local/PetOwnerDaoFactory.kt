package pe.edu.upc.upet.feature_profile.data.local

import pe.edu.upc.upet.MyApplication
import pe.edu.upc.upet.core_database.AppDatabase

class PetOwnerDaoFactory private constructor(){
    companion object{
        fun getPetOwnerDao(): PetOwnerDao{
            return AppDatabase.getInstance(MyApplication.getContext()).getPetOwnerDao()
        }
    }
}