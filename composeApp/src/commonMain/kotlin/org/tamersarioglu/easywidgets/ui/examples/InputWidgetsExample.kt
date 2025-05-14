package org.tamersarioglu.easywidgets.ui.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun InputWidgetsExample() {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Input Widgets Examples",
            style = MaterialTheme.typography.headlineMedium
        )

        // Checkbox example
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Checkboxes",
                    style = MaterialTheme.typography.titleLarge
                )
                
                val checkboxItems = remember { mutableStateListOf(false, false, false) }
                val labels = listOf("Option 1", "Option 2", "Option 3")
                
                labels.forEachIndexed { index, label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = checkboxItems[index],
                            onCheckedChange = { checkboxItems[index] = it }
                        )
                        Text(text = label, modifier = Modifier.padding(start = 8.dp))
                    }
                }
                
                Text(
                    "Selected options: ${labels.filterIndexed { index, _ -> checkboxItems[index] }.joinToString()}"
                )
            }
        }

        // Radio buttons example
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Radio Buttons",
                    style = MaterialTheme.typography.titleLarge
                )
                
                val radioOptions = listOf("Option A", "Option B", "Option C")
                var selectedOption by remember { mutableStateOf(radioOptions[0]) }
                
                radioOptions.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = { selectedOption = option }
                        )
                        Text(
                            text = option,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                
                Text("Selected: $selectedOption")
            }
        }

        // Switch example
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Switches",
                    style = MaterialTheme.typography.titleLarge
                )
                
                var darkModeEnabled by remember { mutableStateOf(false) }
                var notificationsEnabled by remember { mutableStateOf(true) }
                var dataSync by remember { mutableStateOf(false) }
                
                SwitchWithLabel(
                    label = "Dark Mode",
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it }
                )
                
                SwitchWithLabel(
                    label = "Enable Notifications",
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
                
                SwitchWithLabel(
                    label = "Background Data Sync",
                    checked = dataSync,
                    onCheckedChange = { dataSync = it }
                )
                
                Divider()
                
                Text("Settings enabled: ${
                    listOfNotNull(
                        if (darkModeEnabled) "Dark Mode" else null,
                        if (notificationsEnabled) "Notifications" else null,
                        if (dataSync) "Data Sync" else null
                    ).joinToString()}")
            }
        }

        // Slider example
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Sliders",
                    style = MaterialTheme.typography.titleLarge
                )
                
                var volumeLevel by remember { mutableFloatStateOf(0.5f) }
                var brightness by remember { mutableFloatStateOf(0.75f) }
                
                Text("Volume: ${(volumeLevel * 100).roundToInt()}%")
                Slider(
                    value = volumeLevel,
                    onValueChange = { volumeLevel = it },
                    valueRange = 0f..1f
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text("Brightness: ${(brightness * 100).roundToInt()}%")
                Slider(
                    value = brightness,
                    onValueChange = { brightness = it },
                    valueRange = 0f..1f
                )
                
                // Slider with steps
                var progress by remember { mutableFloatStateOf(20f) }
                Text("Progress: ${progress.roundToInt()}/100")
                Slider(
                    value = progress,
                    onValueChange = { progress = it },
                    valueRange = 0f..100f,
                    steps = 10
                )
            }
        }
    }
}

@Composable
fun SwitchWithLabel(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
} 