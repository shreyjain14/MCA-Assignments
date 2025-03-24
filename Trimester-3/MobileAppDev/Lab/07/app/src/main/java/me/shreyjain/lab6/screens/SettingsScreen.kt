package me.shreyjain.lab6.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.shreyjain.lab6.SharedViewModel

@Composable
fun SettingsScreen(viewModel: SharedViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Dark Mode Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Dark Mode",
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = "Dark Mode",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Switch(
                checked = viewModel.isDarkMode.value,
                onCheckedChange = { viewModel.toggleDarkMode() }
            )
        }
        
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        
        // Notifications Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = "Enable Notifications",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Switch(
                checked = viewModel.notificationEnabled.value,
                onCheckedChange = { viewModel.toggleNotifications() }
            )
        }
        
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        
        // Clear Chat History
        Button(
            onClick = { viewModel.clearChat() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Clear Chat",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Clear Chat History")
        }
    }
} 