package com.example.seriesapp.utils

sealed class ApiCallState{
    data class NoInternetErrorMessage(val data:String) : ApiCallState()
    data class GeneralErrorMessage(val data:String) : ApiCallState()
    data class Success(val data:String) : ApiCallState()
    data class NoResults(val data:String) : ApiCallState()
}