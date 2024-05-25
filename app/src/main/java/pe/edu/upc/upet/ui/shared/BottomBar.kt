package pe.edu.upc.upet.ui.shared
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
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
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.theme.Pink


sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    data object Home : Destinations(route = Routes.Home, title= "Home", icon = Icons.Default.Home)
    data object Pets : Destinations(route = Routes.PetList, title= "Pets", icon= Icons.Default.Pets)
    data object Vets : Destinations(route = Routes.VetList,title= "Vets",icon= Icons.Default.LocalHospital)
    data object Events : Destinations(route = Routes.AppointmentList,title= "Events",icon= Icons.Default.Event)
    data object Profile : Destinations(route = Routes.Profile, title="Profile",icon= Icons.Default.AccountCircle)


}

@Composable
fun BottomBar(navController: NavHostController, shouldShowBottomBar: MutableState<Boolean>){
    val items = listOf(
        Destinations.Home, Destinations.Pets, Destinations.Vets, Destinations.Events, Destinations.Profile
    )
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
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Pink,
                    unselectedTextColor = Color.Gray, selectedTextColor = Pink,

                ),

                )
        }
    }
}