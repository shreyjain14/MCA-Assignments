package me.shreyjain.lab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.shreyjain.lab6.screens.HomeScreen
import me.shreyjain.lab6.screens.ProfileScreen
import me.shreyjain.lab6.screens.SettingsScreen
import me.shreyjain.lab6.screens.NotificationsScreen
import me.shreyjain.lab6.ui.theme.Lab6Theme
import android.widget.Toast

sealed class Screen(val route: String, val icon: @Composable () -> Unit, val label: String) {
    object Home : Screen("home", { Icon(Icons.Filled.Home, contentDescription = "Home") }, "Home")
    object Profile : Screen("profile", { Icon(Icons.Filled.Person, contentDescription = "Profile") }, "Profile")
    object Settings : Screen("settings", { Icon(Icons.Filled.Settings, contentDescription = "Settings") }, "Settings")
    object Notifications : Screen("notifications", { Icon(Icons.Filled.Notifications, contentDescription = "Notifications") }, "Notifications")
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab6Theme {
                val navController = rememberNavController()
                val viewModel: SharedViewModel = viewModel()
                val context = LocalContext.current
                
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                
                // State for dropdown menu
                var showMenu by remember { mutableStateOf(false) }
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "Input Controls") },
                                actions = {
                                    // App bar menu button
                                    IconButton(onClick = { showMenu = true }) {
                                        Icon(Icons.Filled.MoreVert, contentDescription = "More options")
                                    }
                                    
                                    // App bar dropdown menu
                                    DropdownMenu(
                                        expanded = showMenu,
                                        onDismissRequest = { showMenu = false }
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("Clear Chat") },
                                            onClick = {
                                                viewModel.clearChat()
                                                showMenu = false
                                                Toast.makeText(context, "Chat cleared", Toast.LENGTH_SHORT).show()
                                            },
                                            leadingIcon = {
                                                Icon(Icons.Filled.Delete, contentDescription = null)
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("About") },
                                            onClick = {
                                                showMenu = false
                                                Toast.makeText(context, "Input Controls App v1.0", Toast.LENGTH_SHORT).show()
                                            },
                                            leadingIcon = {
                                                Icon(Icons.Filled.Info, contentDescription = null)
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Exit") },
                                            onClick = {
                                                showMenu = false
                                                finish()
                                            },
                                            leadingIcon = {
                                                Icon(Icons.Filled.ExitToApp, contentDescription = null)
                                            }
                                        )
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            NavigationBar {
                                val items = listOf(
                                    Screen.Home,
                                    Screen.Profile,
                                    Screen.Settings,
                                    Screen.Notifications
                                )
                                
                                items.forEach { screen ->
                                    NavigationBarItem(
                                        icon = { screen.icon() },
                                        label = { Text(screen.label) },
                                        selected = currentRoute == screen.route,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.startDestinationId)
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Home.route,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            composable(Screen.Home.route) { HomeScreen(viewModel) }
                            composable(Screen.Profile.route) { ProfileScreen(viewModel) }
                            composable(Screen.Settings.route) { SettingsScreen(viewModel) }
                            composable(Screen.Notifications.route) { NotificationsScreen(viewModel) }
                        }
                    }
                }
            }
        }
    }
}