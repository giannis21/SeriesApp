package com.example.seriesapp.network

import com.example.seriesapp.data.characters.CharactersModel
import com.example.seriesapp.data.series.Series
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiClient {

    @GET("characters")
    suspend fun getCharacters(@Query("series") series:String,
                              @Query("limit") limit:String ,
                              @Query("offset") offset:String) : Response<CharactersModel>


    @GET("series")
    suspend fun getSeries(): Response<Series>


}