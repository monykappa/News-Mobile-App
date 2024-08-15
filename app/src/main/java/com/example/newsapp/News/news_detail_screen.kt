package com.example.newsapp.News


import android.content.Intent
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
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun ArticleContent(article: Article) {
    var expanded by remember { mutableStateOf(false) }
    val maxCharacters = 200 // Adjust as needed

    val truncatedContent = if (article.content?.length ?: 0 > maxCharacters) {
        article.content?.substring(0, maxCharacters) + "..."
    } else {
        article.content
    }

    Column {
        (if (expanded) {
            article.content ?: "No content available"
        } else {
            truncatedContent
        })?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (article.content?.length ?: 0 > maxCharacters) {
            Button(onClick = { expanded = !expanded }) {
                Text(text = if (expanded) "Read Less" else "Read More")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(articleId: String, navController: NavController, viewModel: NewsViewModel = viewModel()) {
    val article by viewModel.getArticleById(articleId).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Article Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            article?.let {
                Text(
                    text = it.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                it.urlToImage?.let { imageUrl ->
                    Image(
                        painter = rememberImagePainter(imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(240.dp)
                            .padding(bottom = 16.dp)
                    )
                }
                ArticleContent(article = it)
            } ?: run {
                Text(text = "Article not found", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}


