package com.example.assignment.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.assignment.apis.CategoryAPI
import com.example.assignment.apis.RetrofitInstance
import com.example.assignment.data.dto.CategoryDTO
import com.example.assignment.data.repositorys.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val categoryService: CategoryAPI by lazy {
        RetrofitInstance.categoryService
    }

    private val categoryRepository: CategoryRepository by lazy {
        CategoryRepository(categoryService)
    }

    fun getCategory(onSuccess: (CategoryDTO.GetAllCategoryResponse)-> Unit, onError: (e: Any?)->Unit){
        viewModelScope.launch {
            val response = categoryRepository.getCategory()
            if(response.isSuccessful){
                if(response.body() != null){
                    onSuccess(response.body()!!)
                }
            }else{
                onError(response.errorBody());
            }
        }
    }

    class CategoryViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
                return CategoryViewModel(app) as T
            }

            throw IllegalArgumentException("Unable construct view model")
        }
    }
}