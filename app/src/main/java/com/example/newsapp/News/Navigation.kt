package com.example.newsapp.News


import NewsScreen
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp




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
    BottomNavigation(
        backgroundColor = Color.White, // Set background color to white
        elevation = 0.dp // Remove elevation to ensure border appears as intended
    ) {
        BottomNavigationItem(
            icon = {
                Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.Black)
            },
            label = {
                Text("Home", color = Color.Black)
            },
            selected = navController.currentBackStackEntry?.destination?.route == "home",
            onClick = { navController.navigate("home") { launchSingleTop = true } }
        )
        BottomNavigationItem(
            icon = {
                Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.Black)
            },
            label = {
                Text("Search", color = Color.Black)
            },
            selected = navController.currentBackStackEntry?.destination?.route == "search",
            onClick = { navController.navigate("search") { launchSingleTop = true } }
        )

        BottomNavigationItem(
            icon = {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorite", tint = Color.Black)
            },
            label = {
                Text("Favorite", color = Color.Black)
            },
            selected = navController.currentBackStackEntry?.destination?.route == "favorite",
            onClick = { navController.navigate("favorite") { launchSingleTop = true } }
        )
        BottomNavigationItem(
            icon = {
                Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.Black)
            },
            label = {
                Text("Settings", color = Color.Black)
            },
            selected = navController.currentBackStackEntry?.destination?.route == "settings",
            onClick = { navController.navigate("settings") { launchSingleTop = true } }
        )
    }
    // Apply a black top border
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Black) // Set the top border color to black
    )
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