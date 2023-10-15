package com.apcoding.wallpaperapp.data.remote

import com.apcoding.wallpaperapp.BuildConfig
import com.apcoding.wallpaperapp.model.SearchResult
import com.apcoding.wallpaperapp.model.UnsplashImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    companion object {
        const val API_KEY = "raJPbFU_pmycnIVsLZYh9kbW1njseWQjePEjSp4w2Cw"
    }
    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/photos")
    suspend fun getAllImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<UnsplashImage>

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchResult
}