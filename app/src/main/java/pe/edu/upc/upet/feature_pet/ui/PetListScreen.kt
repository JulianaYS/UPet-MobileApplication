package pe.edu.upc.upet.feature_pet.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.feature_pet.domain.Pets
import pe.edu.upc.upet.feature_pet.domain.pets

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PetScreen(onBack: () -> Unit) {

    val petsState = remember { mutableStateOf(pets) }
    val backgroundColor = Color(0xFF0B1C3F)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text("My Pets")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {paddingValues->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(petsState.value) { pet ->
                PetListScreen(pet, deletePet = {
                    petsState.value = petsState.value.filter { it != pet }
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetListScreen(pet: Pet, deletePet: () -> Unit) {
    val dismissState = rememberSwipeToDismissBoxState(
    )
    LaunchedEffect(key1 = dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd) {

            deletePet()

        }
        dismissState.reset()
    }
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Box(
                modifier = Modifier.fillMaxSize().background(Color(0xFFEB5569)).padding(horizontal = 15.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete Pet",
                    tint = Color.White
                )
            }
        },
        content = { PetCard(pet = pet) },
        modifier = Modifier.fillMaxWidth().padding(15.dp).clip(RoundedCornerShape(20.dp)),
        enableDismissFromEndToStart = false,
        enableDismissFromStartToEnd = true,
    )
}

@Composable
fun PetCard(pet: Pet) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Image(
                painter = rememberImagePainter(pet.imageUrl),
                contentDescription = "Pet Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(10.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = pet.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.Black
                    )
                    Text(
                        text = "Breed: ${pet.breed}",
                        color = Color.Gray
                    )
                    Text(
                        text = "Age: ${pet.age}",
                        color = Color.Gray
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(Color(0xFFEB5569)),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text("View")
                    }
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(Color(0xFFEB5569)),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text("Edit")
                    }
                }
            }
        }
    }
}
