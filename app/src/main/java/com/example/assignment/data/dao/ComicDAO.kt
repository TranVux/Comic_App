package com.example.assignment.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment.models.Comic
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComics(listComic: List<Comic>)

    @Query("SELECT * FROM comics")
    fun getAllComic(): Flow<List<Comic>>

    @Query("SELECT * FROM comics WHERE id like :id")
    fun getComicById(id: String): Flow<Comic>

    @Query("DELETE FROM comics")
    suspend fun deleteAllComic()
}