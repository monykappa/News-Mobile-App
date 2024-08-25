package com.example.newsapp.News


import NewsScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
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
            composable("localnews") { LocalNewsScreen(navController) }
            composable("about") { AboutUsScreen(navController) }
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
        backgroundColor = Color.White,
        elevation = 0.dp
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
                Icon(Icons.Filled.Favorite, contentDescription = "Local News", tint = Color.Black)
            },
            label = {
                Text("Local", color = Color.Black)
            },
            selected = navController.currentBackStackEntry?.destination?.route == "localnews",
            onClick = { navController.navigate("localnews") { launchSingleTop = true } }
        )
        BottomNavigationItem(
            icon = {
                Icon(Icons.Filled.Info, contentDescription = "About Us", tint = Color.Black)
            },
            label = {
                Text("About Us", color = Color.Black)
            },
            selected = navController.currentBackStackEntry?.destination?.route == "about",
            onClick = { navController.navigate("about") { launchSingleTop = true } }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Black)
    )
}





@Preview(showBackground = true)
@Composable
fun PreviewNavHost() {
    AppNavHost()
}