package com.example.seriesapp.repositories

import com.example.seriesapp.network.ApiClient
import com.example.seriesapp.data.characters.CharactersModel
import com.example.seriesapp.data.series.Series
import retrofit2.Response
import javax.inject.Inject


class RemoteRepository @Inject constructor(private val my_Api: ApiClient) {

    suspend fun getSeries(): Response<Series> = my_Api.getSeries()

    suspend fun getCharacters(seriesId:String,offset:String,limit:String):Response<CharactersModel> = my_Api.getCharacters(seriesId,limit,offset)

 }