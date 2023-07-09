package com.example.assignment.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment.models.TestModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDAO {
    @Query("SELECT * FROM test")
    fun getAll(): Flow<List<TestModel>>

    @Query("DELETE FROM test")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestModels(list: List<TestModel>);
}