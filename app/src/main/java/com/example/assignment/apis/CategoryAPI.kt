package com.example.assignment.apis

import com.example.assignment.data.dto.CategoryDTO
import retrofit2.Response
import retrofit2.http.GET

interface CategoryAPI {

    @GET("/api/category/getAll.php")
    suspend fun getCategory(): Response<CategoryDTO.GetAllCategoryResponse>
}