package com.example.assignment.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "comics",
)
class Comic(
    @PrimaryKey var id: String,
    var title: String,
    @SerializedName("sub_title") var subTitle: String,
    var country: String,
    var synopsis: String,
    var thumbnail: String,
    var categories: String,
    @SerializedName("author_name") var author: String,
    @SerializedName("publish_by") @ColumnInfo(name = "user") var publishBy: String
) : Serializable {

}