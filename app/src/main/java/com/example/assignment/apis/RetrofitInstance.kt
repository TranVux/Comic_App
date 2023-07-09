package com.example.assignment.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val bareURL: String = "https://random-data-api.com/api/v2/"

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(bareURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val comicApi: ComicApi by lazy {
        retrofit.create(ComicApi::class.java)
    }

    val testApi: TestApi by lazy {
        retrofit.create(TestApi::class.java)
    }
}