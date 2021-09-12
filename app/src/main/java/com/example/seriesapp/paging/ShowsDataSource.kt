package com.example.movierama

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import  com.example.seriesapp.data.characters.Result
import com.example.seriesapp.R
import com.example.seriesapp.repositories.RemoteRepository
import com.example.seriesapp.utils.ApiCallState
import com.example.seriesapp.utils.NoInternetException
import kotlinx.coroutines.*

class ShowsDataSource(var remoteRepository: RemoteRepository, context: Context, var apiCallState:MutableLiveData<ApiCallState>, var id:String) : PositionalDataSource< Result>() {

    val scope= CoroutineScope(Dispatchers.IO)
    private val TAG= ShowsDataSource::class.java.simpleName
    private var total = 0
    private val limit = 5
    private var flag = false

   //only in initial api call i want to show the internet message layout, that's why i use different exception handler
    private val exceptionHandlerInitial = CoroutineExceptionHandler { _, e ->
       if(e is NoInternetException)
          apiCallState.postValue(ApiCallState.NoInternetErrorMessage(context.getString(R.string.connectivity_problem_pull_down_to_refresh)))
       else
          apiCallState.postValue(ApiCallState.GeneralErrorMessage(context.getString(R.string.an_error_occured)))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Result>) {
        println("$TAG loadInitial called ")

        scope.launch(exceptionHandlerInitial) {

            val response=  remoteRepository.getCharacters(seriesId ="24229",offset = 0.toString(),limit = limit.toString() )
            if(response.isSuccessful){
                total= response.body()!!.data.total
                response.body()!!.data.results.let {
                    if(it.isEmpty())
                        apiCallState.postValue(ApiCallState.NoResults("no results found!"))
                    else
                        apiCallState.postValue(ApiCallState.Success("data fetched succesfully!"))

                    callback.onResult(it,0)
                }
            }else{
                apiCallState.postValue(ApiCallState.GeneralErrorMessage("Oups, an error occured! try again later."))
            }

        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Result>) {
        println("$TAG loadRange called ${params.startPosition.toString()} -- total=$total")

        scope.launch {

            val response=  remoteRepository.getCharacters(seriesId = "24229",offset =params.startPosition.toString(),limit = limit.toString())
            if(response.isSuccessful){
                response.body()!!.data.results.let {

                    apiCallState.postValue(ApiCallState.Success("data fetched succesfully!"))
                    callback.onResult(it)

                    if((params.startPosition >= total - limit) && !flag){
                        flag=true
                        lastPageListener?.invoke()
                    }

                }
            }else{
                apiCallState.postValue(ApiCallState.GeneralErrorMessage("Oups, an error occured! try again later."))
            }



        }
    }

    companion object{
        var lastPageListener: (()-> Unit) ?=null
    }
}