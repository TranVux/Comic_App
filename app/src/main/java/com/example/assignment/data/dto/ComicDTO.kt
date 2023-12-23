package com.example.assignment.data.dto

import com.example.assignment.models.Chapter
import com.example.assignment.models.Comic
import com.google.gson.annotations.SerializedName

class ComicDTO {
    class GetAllResponse(val status: String, val result: List<Comic>, val total: Int, @SerializedName("total_page") val totalPage: Int, @SerializedName("current_page") val currentPage: Int){}

    class GetByIdResponse(val status: String, val result: List<Comic>)

    class ResultGetDetailComic(val comic: Comic, val chapters: List<Chapter>)

    class GetDetailComicResponse(val status: String, val result: ResultGetDetailComic)

    class GetSliderComic(val status: String, val result: List<Comic>){}
}