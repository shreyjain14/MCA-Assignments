package me.shreyjain.cia2.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.foundation.layout.WindowInsets
import me.shreyjain.cia2.ui.screens.AllTasksScreen
import me.shreyjain.cia2.ui.screens.LoginScreen
import me.shreyjain.cia2.ui.screens.ProfileScreen
import me.shreyjain.cia2.ui.screens.RegisterScreen
import me.shreyjain.cia2.ui.screens.TaskDetailScreen
import me.shreyjain.cia2.ui.screens.TravelTasksScreen
import me.shreyjain.cia2.viewmodel.TaskViewModel
import me.shreyjain.cia2.viewmodel.UserViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home/{userId}")
    object TaskDetail : Screen("task_detail/{taskId}")
    object AddTask : Screen("add_task")
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    userViewModel: UserViewModel = viewModel(),
    taskViewModel: TaskViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = { user ->
                    // Set the current user ID in the TaskViewModel
                    taskViewModel.setCurrentUserId(user.id)
                    // Navigate to the home screen with the user ID
                    navController.navigate("home/${user.id}") {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                viewModel = userViewModel
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { 
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                viewModel = userViewModel
            )
        }
        
        composable(
            route = "home/{userId}",
            arguments = listOf(
                navArgument("userId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: return@composable
            
            // Set the current user ID in the TaskViewModel
            LaunchedEffect(userId) {
                taskViewModel.setCurrentUserId(userId)
            }
            
            HomeScreen(
                userId = userId,
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onAddTask = { navController.navigate(Screen.AddTask.route) },
                onTaskClick = { taskId -> 
                    navController.navigate("task_detail/$taskId") 
                },
                taskViewModel = taskViewModel
            )
        }
        
        composable(Screen.AddTask.route) {
            TaskDetailScreen(
                taskId = null, // null means we're adding a new task
                onNavigateBack = { navController.popBackStack() },
                viewModel = taskViewModel
            )
        }
        
        composable(
            route = "task_detail/{taskId}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId") ?: return@composable
            
            TaskDetailScreen(
                taskId = taskId,
                onNavigateBack = { navController.popBackStack() },
                viewModel = taskViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userId: Long,
    onLogout: () -> Unit,
    onAddTask: () -> Unit,
    onTaskClick: (Long) -> Unit,
    taskViewModel: TaskViewModel
) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.AllTasks,
        BottomNavItem.TravelTasks,
        BottomNavItem.Profile
    )
    
    // Track current bottom navigation route
    val selectedItemIndex = remember { mutableIntStateOf(0) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Update selected index based on current route
    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            BottomNavItem.AllTasks.route -> selectedItemIndex.intValue = 0
            BottomNavItem.TravelTasks.route -> selectedItemIndex.intValue = 1
            BottomNavItem.Profile.route -> selectedItemIndex.intValue = 2
        }
    }
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = selectedItemIndex.intValue == index,
                        onClick = {
                            selectedItemIndex.intValue = index
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.AllTasks.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.AllTasks.route) {
                AllTasksScreen(
                    onAddTask = onAddTask,
                    onTaskClick = onTaskClick,
                    viewModel = taskViewModel
                )
            }
            
            composable(BottomNavItem.TravelTasks.route) {
                TravelTasksScreen(
                    onAddTask = onAddTask,
                    onTaskClick = onTaskClick,
                    viewModel = taskViewModel
                )
            }
            
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(
                    userId = userId,
                    onLogout = onLogout,
                    userViewModel = viewModel()
                )
            }
        }
    }
} 