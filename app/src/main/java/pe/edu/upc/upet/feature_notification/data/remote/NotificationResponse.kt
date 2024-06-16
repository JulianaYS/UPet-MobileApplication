package pe.edu.upc.upet.feature_notification.data.remote

import java.time.LocalDateTime

data class NotificationResponse(
    val id: Int,
    val petOwnerId: Int,
    val type: String,
    val message: String,
    val datetime: LocalDateTime
)
