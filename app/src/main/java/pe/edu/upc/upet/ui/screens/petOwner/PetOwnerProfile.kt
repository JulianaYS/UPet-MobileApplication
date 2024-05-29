package pe.edu.upc.upet.ui.screens.petOwner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_auth.data.repository.AuthRepository
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import pe.edu.upc.upet.feature_profile.data.remote.PetOwnerResponse
import pe.edu.upc.upet.feature_profile.data.remote.SubscriptionType
import pe.edu.upc.upet.feature_profile.data.repository.PetOwnerRepository
import pe.edu.upc.upet.feature_profile.domain.PetOwner
import pe.edu.upc.upet.feature_vets.data.repository.VetRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.utils.TokenManager



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetOwnerProfile(navController: NavHostController) {

    val tokenData = TokenManager.getUserIdAndRoleFromToken()

    if (tokenData == null) {
        navController.navigate(Routes.UserLogin)
        return
    }

    val (id, _, _) = tokenData

    var petOwner: PetOwner? by remember { mutableStateOf(null) }

    PetOwnerRepository().getPetOwnerById(id) { it ->
        petOwner = it
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue1,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text("My Profile")
                },
                navigationIcon = {
                    CustomReturnButton(navController = navController)
                }
            )
        }
    ) { paddingValues ->
        LazyColumn {
            item {
                Column(modifier = Modifier.padding(paddingValues)) {

                    ProfileImage(
                        url = petOwner?.imageUrl
                            ?: "https://cdn-icons-png.freepik.com/512/8742/8742495.png"
                    )

                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .shadow(10.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {

                        PetOwnerInfo(petOwner = petOwner)

                        ProfileButtons(navController = navController, petOwner = petOwner)
                    }
                }
            }
        }
    }

}

@Composable
fun ProfileButtons(navController: NavHostController, petOwner: PetOwner? = null) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ActionButton(
            onClick = {
                if(petOwner?.subscriptionType == SubscriptionType.BASIC)
                    navController.navigate(Routes.SubscriptionBasicScreen)
                else
                    navController.navigate(Routes.SubscriptionAdvancedScreen)
            },
            text = "Manage Subscription",
            icon = Icons.Default.Star,
            color = Pink,
        )

        ActionButton(
            onClick = {
                navController.navigate(Routes.PetOwnerEditProfile)
            },
            text = "Edit Profile",
            icon = Icons.Default.Edit,
            color = Pink
        )

        ActionButton(
            onClick = {
                AuthRepository().logOut()
                navController.navigate(Routes.UserLogin)
            },
            text = "Logout",
            icon = Icons.AutoMirrored.Filled.Logout,
            color = Pink
        )


    }
}



@Composable
fun ActionButton(onClick: () -> Unit, text: String, icon: ImageVector, color: Color) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color) ,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Icon(imageVector = icon, contentDescription = "$text Icon")
        Text(text, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun PetOwnerInfo(petOwner: PetOwner?) {
    val userEmail = TokenManager.getEmail()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = petOwner?.name ?: "",
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = userEmail ?: "",
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            fontSize = 16.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Location Icon",
                tint = Color(0xFF3F51B5)
            )
            Text(
                text = "Location: ${petOwner?.location}",
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp),
                fontSize = 18.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person4,
                contentDescription = "Phone Icon",
                tint = Color(0xFF3F51B5)
            )
            Text(
                text = "Phone: ${petOwner?.numberPhone}",
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp),
                fontSize = 18.sp
            )
        }

    }

}


@Composable
fun ProfileImage(url: String) {
    Card(
        modifier = Modifier
            .height(height = 250.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)

    ) {
        GlideImage(modifier = Modifier.size(width = 350.dp, height = 170.dp), imageModel = { url })
    }
}