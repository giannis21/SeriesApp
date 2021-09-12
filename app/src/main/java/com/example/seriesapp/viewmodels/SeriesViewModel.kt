package com.example.seriesapp.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movierama.*
import  com.example.seriesapp.data.characters.Result
import com.example.seriesapp.data.series.Series
import com.example.seriesapp.repositories.RemoteRepository
import com.example.seriesapp.utils.ApiCallState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject


class SeriesViewModel @Inject constructor(var remoteRepository: RemoteRepository, var context: Context) : ViewModel() {

    var error=MutableLiveData<Boolean>(false)
    var apiCallState = MutableLiveData<ApiCallState>()
    var currentDetailObj = MutableLiveData<Series?>(null)

    var itemPagedList: LiveData<PagedList<Result>>? = null

    private val config = PagedList.Config.Builder()
        .setPageSize(5)
        .setEnablePlaceholders(false)
        .build()

    lateinit var factory: DataSourceFactory

    fun initFactory(id:String) {
        factory = DataSourceFactory(remoteRepository,context=context, apiCallState = apiCallState,id)
        itemPagedList = LivePagedListBuilder<Int, Result>(factory, config).build()
    }



    fun getSeries() {
        viewModelScope.launch {
            getSeriesFlow()
                .catch {
                    error.postValue(true)
                }.flowOn(Dispatchers.IO)
                .collect {

                    if (it.isSuccessful) {
                        currentDetailObj.postValue(it.body())
                    }else
                        error.postValue(true)

                }
        }
    }

    private fun getSeriesFlow(): Flow<Response<Series>> = flow {
        emit(remoteRepository.getSeries())
    }





}