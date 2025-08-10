package com.example.newsapp.network

import com.example.newsapp.network.model.RecipeDto
import com.example.newsapp.network.responses.RecipeSearchResponse
import retrofit2.http.*

interface RecipeService {

    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): RecipeSearchResponse

    @GET("get")
    suspend fun get(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): RecipeDto

}