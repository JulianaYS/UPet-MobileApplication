package pe.edu.upc.upet.ui.screens.vetviews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.CustomTextField
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeterinaryForms(navController: NavController, appointmentId: Int) {
    var symptomsExpanded by remember { mutableStateOf(false) }
    var physicalExamExpanded by remember { mutableStateOf(false) }
    var diagnosticTestsExpanded by remember { mutableStateOf(false) }
    var diagnosisExpanded by remember { mutableStateOf(false) }
    var symptoms by remember { mutableStateOf("") }
    var physicalExam by remember { mutableStateOf("") }
    var diagnosticTests by remember { mutableStateOf("") }
    var diagnosis by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopBar(navController, "Veterinary Forms")
        },
        modifier = Modifier.padding(16.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ExpandableCard(
                title = "Add Symptoms",
                icon = Icons.Default.NoteAlt,
                expanded = symptomsExpanded,
                onExpandChange = { symptomsExpanded = it }
            ) {
                OutlinedTextField(
                    value = symptoms,
                    onValueChange = { symptoms = it },
                    label = { Text("Symptoms") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                    ),
                )
            }

            ExpandableCard(
                title = "Physical Examination",
                icon = Icons.Default.NoteAlt,
                expanded = physicalExamExpanded,
                onExpandChange = { physicalExamExpanded = it }
            ) {
                OutlinedTextField(
                    value = physicalExam,
                    onValueChange = { physicalExam = it },
                    label = { Text("Physical Examination") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                    ),
                )
            }

            ExpandableCard(
                title = "Tests Results",
                icon = Icons.Default.NoteAlt,
                expanded = diagnosticTestsExpanded,
                onExpandChange = { diagnosticTestsExpanded = it }
            ) {
                OutlinedTextField(
                    value = diagnosticTests,
                    onValueChange = { diagnosticTests = it },
                    label = { Text("Tests Results") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                    ),
                )
            }

            ExpandableCard(
                title = "Diseases",
                icon = Icons.Default.NoteAlt,
                expanded = diagnosisExpanded,
                onExpandChange = { diagnosisExpanded = it }
            ) {
                OutlinedTextField(
                    value = diagnosis,
                    onValueChange = { diagnosis = it },
                    label = { Text("Diseases") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                    ),
                )
            }

            CustomButton( "Save"){
                navController.navigate("home")
            }

        }
    }
}

@Composable
fun ExpandableCard(title: String, icon: ImageVector, expanded: Boolean, onExpandChange: (Boolean) -> Unit, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = UpetBackGroundPrimary
        ),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExpandChange(!expanded) }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                Icon(
                    imageVector = icon,
                    contentDescription = "Info",
                    tint = Pink)
                Text(text = title)
                    }
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
            }

            if (expanded) {
                HorizontalDivider(color = Color.LightGray)
                Box(modifier = Modifier.padding(16.dp)) {
                    content()
                }
            }
        }
    }
}