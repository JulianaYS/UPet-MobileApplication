package pe.edu.upc.upet.feature_vetClinics.data.remote

import com.google.gson.annotations.SerializedName

class VeterinaryClinicRequest (
    @SerializedName("name")
    val name: String,
    @SerializedName("location")
    val location : String,
    @SerializedName("office_hours")
    val office_hours: String,
    @SerializedName("phone_number")
    val phoneNumber: String
)
