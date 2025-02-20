package me.shreyjain.lab_05

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import java.util.Calendar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Checkbox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val calendar = Calendar.getInstance()

    // Form states
    var selectedDropdown by remember { mutableStateOf("Select a Chatbot Model") }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("") }
    var shareData by remember { mutableStateOf(false) }
    var darkTheme by remember { mutableStateOf(false) }
    var selectedChip by remember { mutableStateOf("") }
    var sliderPosition by remember { mutableStateOf(0f) }
    var dateText by remember { mutableStateOf("Pick Launch Date") }
    var timeText by remember { mutableStateOf("Pick Time") }
    var searchQuery by remember { mutableStateOf("") }
    var showSubmitDialog by remember { mutableStateOf(false) }

    // Compute progress based on the following 6 key fields: model, gender, permanolity chip, p-value, date, time.
    val totalFields = 6
    var filledCount = 0
    if (selectedDropdown != "Select a Chatbot Model") filledCount++
    if (selectedGender.isNotEmpty()) filledCount++
    if (selectedChip.isNotEmpty()) filledCount++
    if (sliderPosition > 0f) filledCount++
    if (dateText != "Pick Launch Date") filledCount++
    if (timeText != "Pick Time") filledCount++
    val progress = filledCount / totalFields.toFloat()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("MakeMyChat") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(paddingValues = innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card header at the top
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("MakeMyChat", style = MaterialTheme.typography.titleLarge)
                    Text("Make your own ai bot")
                }
            }

            // 1. Dropdown for AI Model selection
            Text("Select AI Model")
            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = !dropdownExpanded }
            ) {
                OutlinedTextField(
                    value = selectedDropdown,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("AI Model") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    listOf("o3-mini", "r1", "flash 2.0", "3.5 sonnet").forEach { model ->
                        DropdownMenuItem(
                            text = { Text(model) },
                            onClick = {
                                selectedDropdown = model
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // 2. Radio buttons for Bot Gender
            Text("Select Bot Gender")
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (selectedGender == "Male"),
                    onClick = { selectedGender = "Male" }
                )
                Text("Male")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = (selectedGender == "Female"),
                    onClick = { selectedGender = "Female" }
                )
                Text("Female")
            }

            // 3. Checkbox for sharing data
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = shareData,
                    onCheckedChange = { newChecked -> shareData = newChecked }
                )
                Text("Share data with us")
            }

            // 4. Switch for Dark Theme toggle
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = darkTheme,
                    onCheckedChange = { newTheme -> darkTheme = newTheme }
                )
                Text("Dark Theme")
            }

            // 5. Chips for Permanolity (options A, B, C)
            Text("Select Permanolity")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = (selectedChip == "A"),
                    onClick = { selectedChip = "A" },
                    label = { Text("A") }
                )
                FilterChip(
                    selected = (selectedChip == "B"),
                    onClick = { selectedChip = "B" },
                    label = { Text("B") }
                )
                FilterChip(
                    selected = (selectedChip == "C"),
                    onClick = { selectedChip = "C" },
                    label = { Text("C") }
                )
            }

            // 6. Seekbar for Bot P-Value
            Text("Set Bot P-Value: ${sliderPosition.toInt()}")
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = 0f..100f
            )

            // 7. Date Picker for Bot Launch Date
            Text("Bot Launch Date: $dateText")
            Button(onClick = {
                DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        dateText = "$day/${month + 1}/$year"
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }) {
                Text("Select Launch Date")
            }

            // 8. Time Picker for Launch Time
            Text("Launch Time: $timeText")
            Button(onClick = {
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        timeText = String.format("%02d:%02d", hour, minute)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            }) {
                Text("Select Launch Time")
            }

            // 9. Image View (displaying sample image)
            Text("Preview Image")
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Sample Image",
                modifier = Modifier.size(100.dp)
            )

            // 10. Progress Bar (based on form completion)
            Text("Form completion: ${(progress * 100).toInt()}%")
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
            )

            // 11. Submit Button with Alert Dialog
            Button(onClick = { showSubmitDialog = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Submit")
            }

            if (showSubmitDialog) {
                AlertDialog(
                    onDismissRequest = { showSubmitDialog = false },
                    title = { Text("Bot Created") },
                    text = { Text("Made the bot") },
                    confirmButton = {
                        TextButton(onClick = { showSubmitDialog = false }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showSubmitDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            // 12. Divider
            HorizontalDivider()

            // 13. Recycler Viewer with a list of bots (placeholders)
            Text("Your Bots")
            // Search Bar to search bots
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Bots") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            )

            LazyColumn(modifier = Modifier.height(150.dp)) {
                val bots = listOf("Bot 1", "Bot 2", "Bot 3", "Bot 4", "Bot 5")
                items(bots.filter { it.contains(searchQuery, ignoreCase = true) || searchQuery.isEmpty() }) { bot ->
                    Text(bot, modifier = Modifier.padding(8.dp))
                    HorizontalDivider()
                }
            }

            // 14. WebView to load the provided link
            Text("Web Chat Preview")
            AndroidView(
                factory = { ctx ->
                    WebView(ctx).apply {
                        settings.javaScriptEnabled = true
                        loadUrl("http://chat.makemychat.shreyjain.me/4")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
} 