package com.example.assignment.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.assignment.data.dao.ChapterDAO
import com.example.assignment.data.dao.ComicDAO
import com.example.assignment.data.dao.TestDAO
import com.example.assignment.models.Chapter
import com.example.assignment.models.Comic
import com.example.assignment.models.Converters
import com.example.assignment.models.TestModel

@Database(
    entities = [Comic::class, Chapter::class, TestModel::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ComicAppDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME: String = "ComicAppDB"

        @Volatile
        private var instance: ComicAppDatabase? = null

        fun getInstance(context: Context): ComicAppDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ComicAppDatabase::class.java,
                        DATABASE_NAME
                    ).allowMainThreadQueries().build();
                }
                return instance as ComicAppDatabase;
            }
        }
    }

    abstract fun comicDAO(): ComicDAO
    abstract fun ChapterDAO(): ChapterDAO
    abstract fun testDAO(): TestDAO
}