package me.shreyjain.lab6

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

data class UserProfile(
    val name: String,
    val email: String,
    val avatarUrl: String? = null
)

class SharedViewModel : ViewModel() {
    val chatMessages = mutableStateListOf<ChatMessage>()
    val userProfile = mutableStateOf(UserProfile("User", "user@example.com"))
    val isDarkMode = mutableStateOf(false)
    val notificationEnabled = mutableStateOf(true)
    
    fun sendMessage(text: String) {
        chatMessages.add(ChatMessage(text, true))
        // Simulate AI response
        generateAIResponse(text)
    }
    
    private fun generateAIResponse(userMessage: String) {
        // Simple response generation logic
        val response = when {
            userMessage.contains("hello", ignoreCase = true) -> "Hello! How can I help you today?"
            userMessage.contains("how are you", ignoreCase = true) -> "I'm doing well, thank you! How about you?"
            userMessage.contains("bye", ignoreCase = true) -> "Goodbye! Have a great day!"
            else -> "I understand you said: $userMessage. How can I assist you further?"
        }
        
        chatMessages.add(ChatMessage(response, false))
    }
    
    fun updateProfile(name: String, email: String) {
        userProfile.value = userProfile.value.copy(name = name, email = email)
    }
    
    fun toggleDarkMode() {
        isDarkMode.value = !isDarkMode.value
    }
    
    fun toggleNotifications() {
        notificationEnabled.value = !notificationEnabled.value
    }
    
    fun clearChat() {
        chatMessages.clear()
    }
} 