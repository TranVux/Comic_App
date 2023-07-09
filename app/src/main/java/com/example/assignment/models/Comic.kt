package com.example.assignment.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "comics",
)
class Comic(
    @PrimaryKey var id: String,
    var title: String,
    var subTitle: String,
    var country: String,
    var synopsis: String,
    var thumbnail: String,
    var categories: List<Category>,
    var author: Author,
    @ColumnInfo(name = "user") var publishBy: User
) : Serializable {

}