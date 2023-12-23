package com.example.assignment.data.dto

import com.example.assignment.models.Category

class CategoryDTO {
    class GetAllCategoryResponse(val status: String, val result: List<Category>){}

}