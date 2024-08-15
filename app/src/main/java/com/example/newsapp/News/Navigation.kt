package com.example.newsapp.News


import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            Modifier.padding(innerPadding)
        ) {
            composable("home") { NewsScreen(navController) }
            composable("search") { SearchScreen(navController) }
            composable("favorite") { FavoriteScreen(navController) }
            composable("settings") { SettingsScreen(navController) }
            composable("article/{articleId}") { backStackEntry ->
                val articleId = backStackEntry.arguments?.getString("articleId") ?: ""
                ArticleDetailScreen(articleId, navController)
            }
        }
    }
}



@Composable
fun BottomBar(navController: NavHostController) {
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = navController.currentBackStackEntry?.destination?.route == "home",
            onClick = { navController.navigate("home") { launchSingleTop = true } }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            label = { Text("Search") },
            selected = navController.currentBackStackEntry?.destination?.route == "search",
            onClick = { navController.navigate("search") { launchSingleTop = true } }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorite") },
            label = { Text("Favorite") },
            selected = navController.currentBackStackEntry?.destination?.route == "favorite",
            onClick = { navController.navigate("favorite") { launchSingleTop = true } }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = navController.currentBackStackEntry?.destination?.route == "settings",
            onClick = { navController.navigate("settings") { launchSingleTop = true } }
        )
    }
}


@Composable
fun SearchScreen(navController: NavHostController) {
    // Your SearchScreen content here
    Text("Search Screen")
}

@Composable
fun FavoriteScreen(navController: NavHostController) {
    // Your FavoriteScreen content here
    Text("Favorite Screen")
}

@Composable
fun SettingsScreen(navController: NavHostController) {
    // Your SettingsScreen content here
    Text("Settings Screen")
}

@Preview(showBackground = true)
@Composable
fun PreviewNavHost() {
    AppNavHost()
}