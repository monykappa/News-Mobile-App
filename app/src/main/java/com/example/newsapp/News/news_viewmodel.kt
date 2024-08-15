package com.example.newsapp.News

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> get() = _articles

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    init {
        fetchArticles() // Fetch articles when the ViewModel is initialized
    }

    fun fetchArticles() {
        viewModelScope.launch {
            try {
                val response = TheNewsService.getInstance().getArticles()
                if (response.status == "ok") {
                    _articles.value = response.articles
                } else {
                    _error.value = "Failed to fetch articles"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun getArticleById(articleId: String): StateFlow<Article?> {
        return articles.map { articleList ->
            articleList.find { it.url == articleId }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, null)
    }
}
