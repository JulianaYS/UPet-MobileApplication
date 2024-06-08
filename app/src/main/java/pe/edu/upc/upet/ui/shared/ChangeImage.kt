package pe.edu.upc.upet.ui.shared


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_vets.data.repository.VetRepository

@Composable
fun ChangeProfileImageDialog(navController: NavHostController, vetId: String, onDismissRequest: () -> Unit) {
    var newImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        newImageUri = uri
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Change Profile Image") },
        text = {
            if (newImageUri == null) {
                Text("Please select a new profile image.")
            } else {
                GlideImage(
                    imageModel = { newImageUri.toString() },
                    modifier = Modifier.size(200.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )

    Button(
        onClick = {
            launcher.launch("image/*")
        }
    ) {
        Text("Pick New Image")
    }
}