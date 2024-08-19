package com.example.newsapp.News


import NewsList
import android.content.Intent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController



@Composable
fun SearchScreen(navController: NavController, viewModel: NewsViewModel = viewModel()) {
    val articles by viewModel.articles.collectAsState()
    val favoriteArticles by viewModel.favoriteArticles.collectAsState()
    val query = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            query = query.value,
            onQueryChange = { query.value = it },
            onSearch = { /* Perform search with query.value */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val filteredArticles = if (query.value.isNotEmpty()) {
            articles.filter { it.title.contains(query.value, ignoreCase = true) }
        } else {
            articles
        }

        NewsList(
            articles = filteredArticles,
            favoriteArticles = favoriteArticles,
            isFavorite = { article -> viewModel.isFavorite(article) },
            onFavoriteClick = { article -> viewModel.toggleFavorite(article) },
            navController = navController
        )
    }
}



@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Search news...") },
        trailingIcon = {
            IconButton(onClick = onSearch) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}
