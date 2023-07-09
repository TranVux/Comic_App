package com.example.assignment.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "chapters",
    foreignKeys = [ForeignKey(
        entity = Comic::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_comic"),
        onDelete = ForeignKey.CASCADE
    )]
)
class Chapter(
    @PrimaryKey var id: String,
    var title: String,
    var content: String,
    var index: Int,
    var id_comic: String
) : Serializable {

}