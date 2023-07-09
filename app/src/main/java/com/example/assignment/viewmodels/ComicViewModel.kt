package com.example.assignment.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.apis.RetrofitInstance
import com.example.assignment.data.ComicAppDatabase
import com.example.assignment.data.repositorys.ComicRepository
import com.example.assignment.models.Comic

@Suppress("UNCHECKED_CAST")
class ComicViewModel(app: Application) : ViewModel() {
    private val comicApi by lazy {
        RetrofitInstance.comicApi
    }
    private val appDatabase: ComicAppDatabase by lazy {
        ComicAppDatabase.getInstance(app.applicationContext)
    }

    private val comicRepository: ComicRepository by lazy {
        ComicRepository(comicApi, appDatabase)
    }

    fun getAllComic() = {}
    fun getComic(id: String) = {}

    class ComicViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ComicViewModel::class.java)) {
                return ComicViewModel(app) as T
            }

            throw IllegalArgumentException("Unable construct view model")
        }
    }
}