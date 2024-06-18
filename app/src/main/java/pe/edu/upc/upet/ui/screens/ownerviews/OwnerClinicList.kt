package pe.edu.upc.upet.ui.screens.ownerviews

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_vetClinics.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinics.domain.VeterinaryClinic
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.TopBar
import androidx.compose.runtime.setValue
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import pe.edu.upc.upet.R
import kotlin.math.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerClinicList(navController: NavController) {
    val petOwner = getOwner() ?: return

    Scaffold(
        topBar = {
            TopBar(navController = navController, title = "Veterinary Clinics")
        },
        modifier = Modifier.padding(16.dp)
    ) {paddingValues->
        Column(Modifier.padding(paddingValues)) { }
        MapSection(petOwner.location)
    }

}

@Composable
fun MapSection(location: String) {

    val vetClinicRepository = remember { VeterinaryClinicRepository() }
    var vetClinics: List<VeterinaryClinic> by remember { mutableStateOf(emptyList()) }
    var vetClinicsLocations: List<LatLng> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(key1 = vetClinicRepository) {
        vetClinicRepository.getAllVeterinaryClinics { vetClinicsList ->
            vetClinics = vetClinicsList
        }
    }

    val myLatitude = location.split(",")[0].toDouble()
    val myLongitude = location.split(",")[1].toDouble()

    val myLocation = LatLng(myLatitude, myLongitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(myLocation, 15f)
    }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN))
    }

    for (vetClinic in vetClinics) {
        val clinicLocation = vetClinicTransformLocation(vetClinic)
        vetClinicsLocations += clinicLocation
    }

    GoogleMap(
        modifier = Modifier.fillMaxWidth(),
        cameraPositionState = cameraPositionState,
        properties = properties
    ) {

        Marker(
            state = MarkerState(position = myLocation),
            title = "My Location",
            snippet = "Here I am",
        )

        //add clinic markers but before that, calculate the distance between the clinic and the user
        //the maximum distance is 3 km around the user location
        for(vetClinicLocation in vetClinicsLocations){
            val distance = calculateDistance(myLocation, vetClinicLocation)
            //distance between the user and the clinic is less than 3 km so we show the marker
            if(distance <= 3){
                Marker(
                    state = MarkerState(position = vetClinicLocation),
                    title = "Veterinary Clinic",
                    snippet = "Closes to you",
                    //use the icon form the res drawable folder
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.veterinaria_icon)
                )
            }
        }
    }
}

fun calculateDistance(start: LatLng, end: LatLng): Double {
    val earthRadiusKm = 6371

    val dLat = Math.toRadians(end.latitude - start.latitude)
    val dLon = Math.toRadians(end.longitude - start.longitude)
    val lat1 = Math.toRadians(start.latitude)
    val lat2 = Math.toRadians(end.latitude)

    val a = sin(dLat / 2) * sin(dLat / 2) +
            sin(dLon / 2) * sin(dLon / 2) * cos(lat1) * cos(lat2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    //returns the distance in kilometers
    return earthRadiusKm * c
}

//this function transform the location of a veterinary clinic from a string format: "-12.123123,-77.123123"
// to a LatLng object
// it need to be fixed because some locations doesn't have the correct format and the code will crash
fun vetClinicTransformLocation(veterinaryClinic: VeterinaryClinic): LatLng{
    val clinicLocation = veterinaryClinic.location
    Log.d("clinicLocation", clinicLocation)
    //if location is not with cords format string, we return a default location to avoid the app crash
    if(!clinicLocation.contains(",")){
        return LatLng(-12.122280, -76.986250)
    }

    val clinicLatitude = clinicLocation.split(",")[0].toDouble()
    val clinicLongitude = clinicLocation.split(",")[1].toDouble()
    return LatLng(clinicLatitude, clinicLongitude)
}

@Composable
fun VetClinicCard(navController: NavController, veterinaryClinic: VeterinaryClinic) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Routes.OwnerClinicDetails.createRoute(veterinaryClinic.id))
            }
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            GlideImage(
                imageModel = { veterinaryClinic.image_url },
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp)),
                imageOptions = ImageOptions(contentScale = ContentScale.Crop)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) { // Añade este modificador
                Text(
                    text = veterinaryClinic.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Text(
                    text = veterinaryClinic.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = "Operating Hours: ${veterinaryClinic.office_hours_start + " - " + veterinaryClinic.office_hours_end}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}