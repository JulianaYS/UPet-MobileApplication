package pe.edu.upc.upet.ui.shared

import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

fun uploadImage(imageUrl: Uri, callback: (String?, Exception?) -> Unit) {
    MediaManager.get().upload(imageUrl)
        .option("public_id", "olympic_flag")
        .callback(object : UploadCallback {
            override fun onStart(requestId: String) {

            }

            override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {

            }

            override fun onSuccess(requestId: String, resultData: Map<*, *>) {

                var uploadedImageUrl = resultData["url"].toString()
                uploadedImageUrl = uploadedImageUrl.replace("http", "https")
                println("Upload successful: $resultData")
                callback(uploadedImageUrl, null)
            }

            override fun onError(requestId: String, error: ErrorInfo) {

                println("Upload error: ${error.description}")
                callback(null, Exception(error.description))
            }

            override fun onReschedule(requestId: String, error: ErrorInfo) {

            }
        })
        .dispatch()
}