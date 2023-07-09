package com.example.assignment.apis

import com.example.assignment.models.Comic
import retrofit2.http.GET

interface ComicApi {
    companion object {
        const val BASE_URL = "https://random-data-api.com/api/"
    }

    @GET("restaurant/random_restaurant?size=20")
    suspend fun getComics(): List<Comic>
}