package pe.edu.upc.upet.feature_pet.data.repository

import android.util.Log
import pe.edu.upc.upet.feature_pet.data.local.PetDao
import pe.edu.upc.upet.feature_pet.data.local.PetDaoFactory
import pe.edu.upc.upet.feature_pet.data.remote.PetService
import pe.edu.upc.upet.feature_pet.data.remote.PetServiceFactory
import pe.edu.upc.upet.feature_pet.data.remote.PetsResponse
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.feature_pet.domain.Pets
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

