package pe.edu.upc.upet.ui.screens.petMedical

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.InputTextField

@Composable
fun NewTestResultRegister(navController: NavController) {

    Scaffold {paddingValues ->

        val temporalVariable = remember {
            mutableStateOf("")
        }

        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(10.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ){
            CustomReturnButton(navController = navController)
            Text(
                text = "New Results Information",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
            )
            Text(
                text = "Add new results of the medical test",
                fontSize = 14.sp,
            )
            InputTextField(input = temporalVariable, placeholder = "Add test name", label = "Test name*")
            InputTextField(input = temporalVariable, placeholder = "dd/mm/yyyy", label = "Date*")
            InputTextField(input = temporalVariable, placeholder = "Add results", label = "Results*")
            InputTextField(input = temporalVariable, placeholder = "Add observations", label = "Observations")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.popBackStack()},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6262)),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(text = "Save", color = Color.White)
                }
            }
        }
    }

}