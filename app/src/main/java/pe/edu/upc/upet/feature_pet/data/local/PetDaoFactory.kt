package pe.edu.upc.upet.feature_pet.data.local

class PetDaoFactory private constructor(){
    companion object {
        fun getPetDao(): PetDao {
            //return AppDatabase.getInstance(MyApplication.getContext()).getPetDao()
        }
    }
}