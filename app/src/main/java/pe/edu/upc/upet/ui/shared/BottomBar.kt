package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.theme.Pink

data class BottomNavItem(
    val icon: ImageVector,
    val label: String,
    val route: String,
    val action: () -> Unit
)

@Composable
fun BottomBar(shouldShowBottomBar: MutableState<Boolean>, navController: NavController) {
    val items = listOf(
        BottomNavItem(Icons.Default.Home, "Home", Routes.Home, { navController.navigate(Routes.Home) }),
        BottomNavItem(Icons.Default.Pets, "Pets", Routes.PetList, { navController.navigate(Routes.PetList) }),
        BottomNavItem(Icons.Default.LocalHospital, "Vets", Routes.VetList, { navController.navigate(Routes.VetList) }),
        BottomNavItem(Icons.Default.Event, "Appointments", "", { /* Navigate to Appointments screen */ }),
        BottomNavItem(Icons.Default.AccountCircle, "Profile", "", { /* Navigate to Profile screen */ })
    )

    if (shouldShowBottomBar.value) {
        BottomAppBar(
            modifier = Modifier
                .height(65.dp)
                .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
            containerColor = Color.White,
        ) {
            BottomNavigation(backgroundColor = Color.White) {
                items.forEach { item ->
                    BottomNavigationItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.label) },
                        selectedContentColor = Pink,
                        unselectedContentColor = Color.Gray,
                        selected = navController.currentDestination?.route == item.route,
                        onClick = item.action
                    )
                }
            }
        }
    }
}