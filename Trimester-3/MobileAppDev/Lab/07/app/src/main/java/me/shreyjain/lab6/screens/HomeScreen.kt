package me.shreyjain.lab6.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import me.shreyjain.lab6.ChatMessage
import me.shreyjain.lab6.SharedViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: SharedViewModel) {
    var messageText by remember { mutableStateOf("") }
    val context = LocalContext.current
    
    // State for tracking which message has a context menu and its position
    var showContextMenu by remember { mutableStateOf(false) }
    var contextMenuMessage by remember { mutableStateOf<ChatMessage?>(null) }
    
    // State for popup menu
    var showPopupMenu by remember { mutableStateOf(false) }
    
    // State for edit dialog
    var showEditDialog by remember { mutableStateOf(false) }
    var editingMessage by remember { mutableStateOf<ChatMessage?>(null) }
    var editedText by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with popup menu button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Messages", style = MaterialTheme.typography.headlineSmall)
            
            Button(
                onClick = { showPopupMenu = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text("Actions")
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Show menu",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        
        // Edit message dialog
        if (showEditDialog && editingMessage != null) {
            Dialog(onDismissRequest = { showEditDialog = false }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Edit Message",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        
                        OutlinedTextField(
                            value = editedText,
                            onValueChange = { editedText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            label = { Text("Message") },
                            maxLines = 5
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = { showEditDialog = false }
                            ) {
                                Text("Cancel")
                            }
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Button(
                                onClick = {
                                    editingMessage?.let { originalMessage ->
                                        // Find message in the list and update it
                                        val index = viewModel.chatMessages.indexOf(originalMessage)
                                        if (index != -1 && editedText.isNotBlank()) {
                                            val updatedMessage = originalMessage.copy(text = editedText)
                                            viewModel.chatMessages[index] = updatedMessage
                                            Toast.makeText(context, "Message updated", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    showEditDialog = false
                                }
                            ) {
                                Text("Save")
                            }
                        }
                    }
                }
            }
        }
        
        // Popup menu dialog
        if (showPopupMenu) {
            Dialog(onDismissRequest = { showPopupMenu = false }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Quick Actions",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        
                        ListItem(
                            headlineContent = { Text("Clear All Messages") },
                            leadingContent = { Icon(Icons.Filled.Delete, contentDescription = null) },
                            modifier = Modifier.clickable {
                                viewModel.clearChat()
                                showPopupMenu = false
                                Toast.makeText(context, "All messages cleared", Toast.LENGTH_SHORT).show()
                            }
                        )
                        
                        ListItem(
                            headlineContent = { Text("Send Greeting") },
                            leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                            modifier = Modifier.clickable {
                                viewModel.sendMessage("Hello, how are you today?")
                                showPopupMenu = false
                            }
                        )
                        
                        ListItem(
                            headlineContent = { Text("Send Location") },
                            leadingContent = { Icon(Icons.Filled.LocationOn, contentDescription = null) },
                            modifier = Modifier.clickable {
                                viewModel.sendMessage("I'm sharing my current location")
                                showPopupMenu = false
                            }
                        )
                        
                        Button(
                            onClick = { showPopupMenu = false },
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(top = 8.dp)
                        ) {
                            Text("Close")
                        }
                    }
                }
            }
        }
        
        // Chat messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            items(viewModel.chatMessages.asReversed()) { message ->
                ChatBubble(
                    message = message,
                    modifier = Modifier.padding(vertical = 4.dp),
                    onLongClick = {
                        contextMenuMessage = message
                        showContextMenu = true
                    }
                )
            }
        }
        
        // Context menu
        if (showContextMenu && contextMenuMessage != null) {
            DropdownMenu(
                expanded = showContextMenu,
                onDismissRequest = { showContextMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Copy") },
                    onClick = {
                        Toast.makeText(context, "Message copied", Toast.LENGTH_SHORT).show()
                        showContextMenu = false
                    },
                    leadingIcon = { Icon(Icons.Filled.Info, contentDescription = null) }
                )
                
                if (contextMenuMessage?.isUser == true) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = {
                            contextMenuMessage?.let { message ->
                                editingMessage = message
                                editedText = message.text
                                showEditDialog = true
                            }
                            showContextMenu = false
                        },
                        leadingIcon = { Icon(Icons.Filled.Edit, contentDescription = null) }
                    )
                }
                
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = {
                        contextMenuMessage?.let { message ->
                            viewModel.chatMessages.remove(message)
                            Toast.makeText(context, "Message deleted", Toast.LENGTH_SHORT).show()
                        }
                        showContextMenu = false
                    },
                    leadingIcon = { Icon(Icons.Filled.Delete, contentDescription = null) }
                )
                
                DropdownMenuItem(
                    text = { Text("Share") },
                    onClick = {
                        contextMenuMessage?.let { message ->
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, message.text)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, "Share message")
                            context.startActivity(shareIntent)
                        }
                        showContextMenu = false
                    },
                    leadingIcon = { Icon(Icons.Filled.Share, contentDescription = null) }
                )
            }
        }
        
        // Message input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Type a message...") },
                maxLines = 3
            )
            
            IconButton(
                onClick = {
                    if (messageText.isNotBlank()) {
                        viewModel.sendMessage(messageText)
                        messageText = ""
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Send message"
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatBubble(
    message: ChatMessage,
    modifier: Modifier = Modifier,
    onLongClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isUser) 16.dp else 4.dp,
                bottomEnd = if (message.isUser) 4.dp else 16.dp
            ),
            color = if (message.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier
                .widthIn(max = 300.dp)
                .combinedClickable(
                    onClick = { /* Normal click does nothing */ },
                    onLongClick = onLongClick
                )
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                color = if (message.isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
} 