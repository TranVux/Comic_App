package com.example.assignment.data.dao

import androidx.room.*
import com.example.assignment.models.Chapter
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(vararg chapters: Chapter)

    @Query("SELECT * FROM chapters")
    fun getAllChapter(): Flow<List<Chapter>>

    @Query("SELECT * FROM chapters WHERE id_comic like :idComic")
    fun getAllChapterOfComic(idComic: String): Flow<List<Chapter>>

    @Query("SELECT * FROM chapters WHERE id like :id")
    fun getChapterById(id: String): Flow<Chapter>

    @Query("DELETE FROM chapters")
    suspend fun deleteAllChapter()
}