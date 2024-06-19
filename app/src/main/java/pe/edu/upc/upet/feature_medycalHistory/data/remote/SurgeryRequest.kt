package pe.edu.upc.upet.feature_medycalHistory.data.remote

data class SurgeryRequest(
    val surgeryDate: String,
    val description: String,
    val medicalHistoryId: Int
)