package me.shreyjain.lab6.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.shreyjain.lab6.SharedViewModel

@Composable
fun ProfileScreen(viewModel: SharedViewModel) {
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(viewModel.userProfile.value.name) }
    var email by remember { mutableStateOf(viewModel.userProfile.value.email) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture
        Surface(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .padding(24.dp)
                    .size(72.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        if (isEditing) {
            // Edit Mode
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        viewModel.updateProfile(name, email)
                        isEditing = false
                    }
                ) {
                    Text("Save")
                }
                
                OutlinedButton(
                    onClick = {
                        name = viewModel.userProfile.value.name
                        email = viewModel.userProfile.value.email
                        isEditing = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        } else {
            // View Mode
            Text(
                text = viewModel.userProfile.value.name,
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = viewModel.userProfile.value.email,
                style = MaterialTheme.typography.bodyLarge
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = { isEditing = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Edit Profile")
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Chat Statistics
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Chat Statistics",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Total Messages: ${viewModel.chatMessages.size}")
                Text("Your Messages: ${viewModel.chatMessages.count { it.isUser }}")
                Text("AI Responses: ${viewModel.chatMessages.count { !it.isUser }}")
            }
        }
    }
} 