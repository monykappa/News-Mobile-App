package com.example.newsapp.News


import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController



@Composable
fun NewsScreen(navController: NavController, viewModel: NewsViewModel = viewModel()) {
    val articles by viewModel.articles.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (error != null) {
            Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
        } else if (articles.isEmpty()) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(articles) { article ->
                    ArticleItem(article, navController)
                }
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                val encodedUrl = Uri.encode(article.url)
                navController.navigate("article/$encodedUrl")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            article.urlToImage?.let {
                Image(
                    painter = rememberImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(end = 16.dp)
                )
            }
            Column {
                Text(text = article.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = article.description ?: "", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
