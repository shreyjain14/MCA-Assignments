package me.shreyjain.lab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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

sealed class Screen(val route: String, val icon: @Composable () -> Unit, val label: String) {
    object Home : Screen("home", { Icon(Icons.Default.Home, contentDescription = "Home") }, "Home")
    object Profile : Screen("profile", { Icon(Icons.Default.Person, contentDescription = "Profile") }, "Profile")
    object Settings : Screen("settings", { Icon(Icons.Default.Settings, contentDescription = "Settings") }, "Settings")
    object Notifications : Screen("notifications", { Icon(Icons.Default.Notifications, contentDescription = "Notifications") }, "Notifications")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab6Theme {
                val navController = rememberNavController()
                val viewModel: SharedViewModel = viewModel()
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentRoute = navBackStackEntry?.destination?.route
                                
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