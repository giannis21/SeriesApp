package com.example.seriesapp.network
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.seriesapp.utils.NoInternetException

import okhttp3.Interceptor

import okhttp3.Response
import javax.inject.Inject


class NetworkConnectionIncterceptor @Inject constructor(context: Context) : Interceptor {

    private val applicationContext: Context = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
       if(!isInternetAvailable(applicationContext))
           throw NoInternetException("No internet connection")
        return chain.proceed(chain.request())
    }


    fun isInternetAvailable(applicationContext: Context): Boolean {
        var result = false
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }
}