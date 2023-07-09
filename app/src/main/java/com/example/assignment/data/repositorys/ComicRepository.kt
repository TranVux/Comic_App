package com.example.assignment.data.repositorys

import androidx.room.withTransaction
import com.example.assignment.apis.ComicApi
import com.example.assignment.data.ComicAppDatabase
import com.example.assignment.utils.networkBoundResource
import kotlinx.coroutines.delay

class ComicRepository(
    private val api: ComicApi,
    private val db: ComicAppDatabase
) {
    private val comicDAO = db.comicDAO()

    fun getComics() = networkBoundResource(
        query = {
            comicDAO.getAllComic()
        },
        fetch = {
            delay(100)
            api.getComics()
        },
        saveFetchResult = { comics ->
            db.withTransaction {
                comicDAO.deleteAllComic()
                comicDAO.insertComics(comics)
            }
        }
    )
}