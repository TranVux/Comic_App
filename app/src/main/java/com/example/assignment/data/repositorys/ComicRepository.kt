package com.example.assignment.data.repositorys

import androidx.room.withTransaction
import com.example.assignment.apis.ComicApi
import com.example.assignment.data.ComicAppDatabase
import com.example.assignment.utils.networkBoundResource
import kotlinx.coroutines.delay

class ComicRepository(
    private val api: ComicApi,
) {
    suspend fun getComics(page: Int = 1, limit: Int = 8) = api.getComics(limit, page);
    suspend fun getComicById(id:String) = api.getComicById(id)
    suspend fun getDetailComic(id: String) = api.getDetailComic(id)
    suspend fun getSliderComic() = api.getSlider();
}