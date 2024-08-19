package com.example.newsapp.News

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val News_Base_Url = "https://newsapi.org/v2/"

interface TheNewsService {
    @GET("everything")
    suspend fun getArticles(
        @Query("q") query: String = "Latest", // Default query for the home screen
        @Query("apiKey") apiKey: String = "0ce2d9d56ce548108c9f87e7f3827bf5",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 50
    ): ApiResponse

    @GET("everything")
    suspend fun getArticlesWithQuery(
        @Query("q") query: String? = null, // Null query for custom searches
        @Query("apiKey") apiKey: String = "0ce2d9d56ce548108c9f87e7f3827bf5",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 50
    ): ApiResponse

    companion object {
        private var apiService: TheNewsService? = null
        fun getInstance(): TheNewsService {
            if (apiService == null) {
                val gson = GsonBuilder().setLenient().create()
                apiService = Retrofit.Builder()
                    .baseUrl(News_Base_Url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(TheNewsService::class.java)
            }
            return apiService!!
        }
    }
}

