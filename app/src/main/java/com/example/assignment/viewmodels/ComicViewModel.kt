package com.example.assignment.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.assignment.apis.RetrofitInstance
import com.example.assignment.data.ComicAppDatabase
import com.example.assignment.data.dto.ComicDTO
import com.example.assignment.data.repositorys.ComicRepository
import com.example.assignment.models.Comic
import com.example.assignment.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
class ComicViewModel(app: Application) : ViewModel() {
    private val comicApi by lazy {
        RetrofitInstance.comicApi
    }

    private val comicRepository: ComicRepository by lazy {
        ComicRepository(comicApi)
    }

    fun getAllComic(
        page: Int = 1,
        limit: Int = 8,
        onSuccess: (ComicDTO.GetAllResponse) -> Unit,
        onError: (error: Any?) -> Unit
    ) {
        viewModelScope.launch {
            val response = comicRepository.getComics(page, limit)
            if (response.isSuccessful) {
                if (response.body() != null) onSuccess(response.body()!!)
                else onError(Throwable("Empty List Comic"))
            } else {
                onError(response.errorBody())
            }
        }
    }

    fun getComicById(
        id: String,
        onSuccess: (ComicDTO.GetByIdResponse) -> Unit,
        onError: (error: Any?) -> Unit
    ) {
        viewModelScope.launch {
            val response = comicRepository.getComicById(id);
            if (response.isSuccessful) {
                if (response.body() != null) onSuccess(response.body()!!)
                else onError(Throwable("Error comic"))
            } else {
                onError(response.errorBody())
            }
        }
    }

    fun getDetailComic(
        id: String,
        onSuccess: (ComicDTO.GetDetailComicResponse) -> Unit,
        onError: (error: Any?) -> Unit
    ) {
        viewModelScope.launch {
            val response = comicRepository.getDetailComic(id);
            if (response.isSuccessful) {
                if (response.body() != null) onSuccess(response.body()!!)
                else onError(Throwable("Error comic"))
            } else {
                onError(response.errorBody())
            }
        }
    }

    fun getSliderComic(
        onSuccess: (ComicDTO.GetSliderComic) -> Unit,
        onError: (error: Any?) -> Unit
    ) {
        viewModelScope.launch {
            val response = comicRepository.getSliderComic();
            if (response.isSuccessful) {
                if (response.body() != null) onSuccess(response.body()!!)
                else onError(Throwable("Error comic"))
            } else {
                onError(response.errorBody())
            }
        }
    };
    class ComicViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ComicViewModel::class.java)) {
                return ComicViewModel(app) as T
            }

            throw IllegalArgumentException("Unable construct view model")
        }
    }
}