package com.example.assignment.data.repositorys

import androidx.room.withTransaction
import com.example.assignment.apis.TestApi
import com.example.assignment.data.ComicAppDatabase
import com.example.assignment.utils.networkBoundResource
import kotlinx.coroutines.delay

class TestRepository(
    private val api: TestApi,
    private val db: ComicAppDatabase
) {
    //    private val comicDAO = db.comicDAO()
    private val testDAO = db.testDAO()

    fun getTest() = networkBoundResource(
        query = {
            testDAO.getAll()
        },
        fetch = {
            delay(100)
            api.getTestData()
        },
        saveFetchResult = { tests ->
            db.withTransaction {
                testDAO.deleteAll()
                testDAO.insertTestModels(tests)
            }
        }
    )
}