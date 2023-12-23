package com.example.assignment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Category(

    @SerializedName("id")
    var id: String?,

    @SerializedName("title")
    var title: String?,

    @SerializedName("thumbnail")
    var thumbnail: String?,

    @SerializedName("display_color")
    var displayColor: String?,

    @SerializedName("info")
    var info: String?,

    @SerializedName("comic_amount")
    var comicAmount: Int?,

    @SerializedName("create_at")
    var createAt: String?
) : Serializable {
    override fun toString(): String {
        return "$title "
    }
}