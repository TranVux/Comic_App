package com.example.assignment.data.repositorys

import com.example.assignment.apis.CategoryAPI

class CategoryRepository(val api: CategoryAPI) {
    suspend fun getCategory() = api.getCategory()
}