package pe.edu.upc.upet.feature_medycalHistory.data.remote

data class DiseaseResponse(
    val id: Int,
    val name: String,
    val medicalHistoryId: Int,
    val diagnosisDate: String,
    val severity: String
)