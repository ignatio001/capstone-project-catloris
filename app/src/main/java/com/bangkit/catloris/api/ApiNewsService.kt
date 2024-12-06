package com.bangkit.catloris.api

import com.bangkit.catloris.responses.ArticleResponse
import retrofit2.http.GET

interface ApiNewsService {
    @GET("top-headlines?category=health&language=en&apiKey=b3d9dfb6566843a7a09fbd7d55079764")
    suspend fun getHealthArticle(): ArticleResponse
}