package com.example.movierama

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

import com.example.seriesapp.data.characters.Result
import com.example.seriesapp.repositories.RemoteRepository
import com.example.seriesapp.utils.ApiCallState

class DataSourceFactory(var remoteRepository: RemoteRepository, var context: Context, var apiCallState: MutableLiveData<ApiCallState>, var id:String) : DataSource.Factory<Int, Result>() {

    override fun create(): DataSource<Int, Result> {
        return ShowsDataSource(remoteRepository,context = context,apiCallState,id)
    }



}