package com.example.assignment.apis

import com.example.assignment.data.dto.ComicDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicApi {
    companion object {
        const val BASE_URL = "https://random-data-api.com/api/"
    }
    @GET("/api/comic/getAll.php")
    suspend fun getComics(@Query("limit")limit: Int = 8, @Query("page")page: Int = 1): Response<ComicDTO.GetAllResponse>

    @GET("/api/comic/getDetailComic.php")
    suspend fun getDetailComic(@Query("comic_id")comic_id: String = ""): Response<ComicDTO.GetDetailComicResponse>

    @GET("/api/comic/getDetailComic.php")
    suspend fun getComicById(@Query("comic_id")comic_id: String = ""): Response<ComicDTO.GetByIdResponse>

    @GET("api/comic/getSliderComic.php")
    suspend fun getSlider(): Response<ComicDTO.GetSliderComic>
}