package com.example.assignment.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
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
data class Chapter(
    @PrimaryKey var id: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("content")
    var content: String,

    @SerializedName("chapter_index")
    var index: Int,

    @SerializedName("id_comic")
    var id_comic: String,

    @SerializedName("has_html")
    val hasHtml: Int
) : Serializable {

}