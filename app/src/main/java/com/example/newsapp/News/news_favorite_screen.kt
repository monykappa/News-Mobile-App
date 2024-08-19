package com.example.newsapp.News


import ArticleItem
import NewsList
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.ParseException
import coil.compose.rememberImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.newsapp.News.Article
import com.example.newsapp.News.NewsViewModel
import com.example.newsapp.R
import java.util.Date
import java.util.Locale
import android.util.Log



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController, viewModel: NewsViewModel = viewModel()) {
    val favoriteArticles by viewModel.favoriteArticles.collectAsState()

    // Debugging output
    Log.d("FavoriteScreen", "Favorite articles: $favoriteArticles")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite Articles") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .fillMaxSize()
        ) {
            if (favoriteArticles.isEmpty()) {
                Text("No favorite articles yet.", modifier = Modifier.align(Alignment.Center))
            } else {
                NewsList(
                    articles = favoriteArticles,
                    favoriteArticles = favoriteArticles,
                    isFavorite = { article -> viewModel.isFavorite(article) },
                    onFavoriteClick = { article -> viewModel.toggleFavorite(article) },
                    navController = navController
                )
            }
        }
    }
}
