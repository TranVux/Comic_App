package com.example.assignment.apis

import com.example.assignment.models.TestModel
import retrofit2.http.GET

interface TestApi {
    @GET("banks?size=20")
    suspend fun getTestData(): List<TestModel>
}