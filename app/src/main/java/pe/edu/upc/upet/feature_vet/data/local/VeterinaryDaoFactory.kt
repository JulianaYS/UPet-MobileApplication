package pe.edu.upc.upet.feature_vet.data.local

import pe.edu.upc.upet.MyApplication
import pe.edu.upc.upet.core_database.AppDatabase

class VeterinaryDaoFactory private constructor() {
    companion object{
        fun getVeterinaryDao(): VeterinaryDao{
            return AppDatabase.getInstance(MyApplication.getContext()).getVeterinaryDao()
        }
    }
}