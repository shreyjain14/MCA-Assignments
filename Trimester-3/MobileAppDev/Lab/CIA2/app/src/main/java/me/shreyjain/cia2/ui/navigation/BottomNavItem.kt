package me.shreyjain.cia2.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object AllTasks : BottomNavItem(
        route = "all_tasks",
        title = "All Tasks",
        icon = Icons.AutoMirrored.Filled.List
    )
    
    object TravelTasks : BottomNavItem(
        route = "travel_tasks",
        title = "Travel",
        icon = Icons.Default.Place
    )
    
    object Profile : BottomNavItem(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Settings
    )
} 