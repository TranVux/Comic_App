package com.example.assignment.apis

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val bareURL: String = "http://10.0.2.2:3000"
//    private const val bareURL: String = "http://192.168.1.21:3000"
    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okClient by lazy {
        OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor) // same for .addInterceptor(...)
            .connectTimeout(30, TimeUnit.SECONDS) //Backend is really slow
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(bareURL)
            .client(okClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val comicApi: ComicApi by lazy {
        retrofit.create(ComicApi::class.java)
    }

    val testApi: TestApi by lazy {
        retrofit.create(TestApi::class.java)
    }

    val authService: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val categoryService: CategoryAPI by lazy {
        retrofit.create(CategoryAPI::class.java)
    }
}