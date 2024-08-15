package com.example.newsapp.News


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(articleId: String, navController: NavController, viewModel: NewsViewModel = viewModel()) {
    val article by viewModel.getArticleById(articleId).collectAsState(initial = null)
    val isLoading = remember { mutableStateOf(true) }

    // Check if the article is still loading
    LaunchedEffect(article) {
        if (article != null) {
            isLoading.value = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Display truncated article title or "Loading..." if article is not yet available
                    Text(
                        text = article?.title?.let { truncateTitle(it) } ?: "Loading...",
                        maxLines = 1, // Ensure title is on one line
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black // Set the title text color to black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White, // Set the background color of the TopAppBar to white
                    titleContentColor = Color.Black // Ensure the title text color is black
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isLoading.value) {
                // Show a loading indicator while data is being fetched
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                article?.let {
                    // Show WebView for article URL only
                    WebViewContainer(url = it.url)
                } ?: run {
                    // Show a message if the article is not found
                    Text(
                        text = "Article not found",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WebViewContainer(url: String) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            WebView(context).apply {
                webViewClient = WebViewClient() // Handle navigation within WebView
                settings.javaScriptEnabled = true // Enable JavaScript if needed
                loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

fun truncateTitle(title: String, maxWords: Int = 6): String {
    val words = title.split(" ")
    return if (words.size <= maxWords) {
        title
    } else {
        words.take(maxWords).joinToString(" ") + "..."
    }
}
