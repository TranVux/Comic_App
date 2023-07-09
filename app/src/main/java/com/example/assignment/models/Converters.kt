package com.example.assignment.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun toUser(list: String): User {
        return Gson().fromJson(list, User::class.java)
    }

    @TypeConverter
    fun fromUser(users: User): String {
        return Gson().toJson(users)
    }

    @TypeConverter
    fun toCategory(category: String): List<Category>? {
        val listType = object : TypeToken<List<Category>>() {}.type;
        return Gson().fromJson<List<Category>>(category, listType)
    }

    @TypeConverter
    fun fromCategory(category: List<Category>): String {
        return Gson().toJson(category)
    }

    @TypeConverter
    fun toAuthor(author: String): Author {
        return Gson().fromJson(author, Author::class.java);
    }

    @TypeConverter
    fun fromAuthor(author: Author): String {
        return Gson().toJson(author)
    }
}