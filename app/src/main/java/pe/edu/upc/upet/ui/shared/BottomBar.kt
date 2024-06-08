package pe.edu.upc.upet.ui.shared


/*import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import pe.edu.upc.upet.feature_auth.data.remote.UserType
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.theme.Pink


data class BottomNavItem(val name: String, val route: String, val icon: ImageVector)

val ownerBottomNavItems = listOf(
    BottomNavItem("Home", Routes.OwnerHome.route, Icons.Default.Home),
    BottomNavItem("Profile", Routes.OwnerProfile.route, Icons.Default.Person),
    BottomNavItem("Pets", Routes.PetList.route, Icons.Default.Pets),
    BottomNavItem("Appointments", Routes.AppointmentList.route, Icons.Default.Event),
    BottomNavItem("Clinics", Routes.OwnerClinicList.route, Icons.Default.LocalHospital)
)

val vetBottomNavItems = listOf(
    BottomNavItem("Home", Routes.VetHome.route, Icons.Default.Home),
    BottomNavItem("Profile", Routes.VetProfile.route, Icons.Default.Person),
    BottomNavItem("Patients", Routes.VetPatients.route, Icons.Default.Group),
    BottomNavItem("Appointments", Routes.VetAppointments.route, Icons.Default.Event),
)


@Composable
fun BottomBar(navController: NavHostController, shouldShowBottomBar: MutableState<Boolean>, userType: UserType){
    val items = when(userType) {
        UserType.Vet -> listOf(
            Destinations.VetHome, Destinations.Pets, Destinations.Events, Destinations.Profile
        )
        UserType.Owner -> listOf(
            Destinations.OwnerHome, Destinations.Pets, Destinations.Vets, Destinations.Events, Destinations.Profile
        )
    }
    if (!shouldShowBottomBar.value) return
    NavigationBar(modifier = Modifier,
        containerColor = Color.White) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { item.icon?.let { Icon(imageVector = it, contentDescription = null) } },
                label = { Text(item.title ?: "Unknown")},
                selected = currentRoute == item.route,
                onClick = {
                    try {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } catch (e: Exception) {
                        Log.d("NavigationError", "Failed to navigate to ${item.route}: ${e.message}")
                    }
                }
                ,colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Pink,
                    unselectedTextColor = Color.Gray, selectedTextColor = Pink,

                    ),

                )
        }
    }
} */