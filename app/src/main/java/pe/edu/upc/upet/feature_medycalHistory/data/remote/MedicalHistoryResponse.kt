package pe.edu.upc.upet.feature_medycalHistory.data.remote

data class MedicalHistoryResponse(
    val id: Int,
    val petId: Int,
    val date: String,
    val description: String
)