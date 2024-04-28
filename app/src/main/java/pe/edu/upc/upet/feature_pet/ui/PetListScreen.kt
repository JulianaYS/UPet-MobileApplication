package pe.edu.upc.upet.feature_pet.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.feature_pet.domain.pets
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetScreen() {
    val backgroundColor = Color(0x0B1C3F)

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("My Pets")
                }
            )
        }
    ) {paddingValues->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(pets) { pet ->
                PetListScreen(pet)
            }
        }
    }

}

@Composable
fun PetListScreen(pet: Pet) {
    val textColor = Color(0XC2C3CC)
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,  modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Image(
                painter = rememberImagePainter(pet.imageUrl),
                contentDescription = "Pet Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Column() {

                Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                    Text(text = pet.name, color = Color.Black)
                    Text(text = "Breed: ${pet.breed}", color = Color.Gray)
                    Text(text = "Age: ${pet.age}", color =  Color.Gray)
                }

                Row(modifier = Modifier.padding(8.dp)) {
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(Color(0xFFE91E63))
                    ) {
                        Text("View")
                    }
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(Color(0xFFE91E63))
                    ) {
                        Text("Edit")
                    }
                }
            }
        }
    }
}