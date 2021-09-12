package com.example.seriesapp.di


import com.example.seriesapp.network.ApiClient
import com.example.seriesapp.network.NetworkConnectionIncterceptor
import com.example.seriesapp.utils.Constants
import com.example.seriesapp.utils.md5
import com.example.seriesapp.utils.toHex
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    internal fun provideRetrofitInterface(networkConnectionIncterceptor: NetworkConnectionIncterceptor): ApiClient {

        val interceptor = Interceptor { chain ->
            val paramsObjMap= md5().toHex()
            val hash = paramsObjMap.keys.first()
            val ts= paramsObjMap.getValue(hash)

            val url = chain.request().url.newBuilder()
                .addQueryParameter("apikey", Constants.API_KEY)
                .addQueryParameter("ts", ts)
                .addQueryParameter("hash", hash).build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            val response = chain.proceed(request)
            response
        }


        val logging = HttpLoggingInterceptor()
        logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }

        val okHttpClient1 = OkHttpClient.Builder()
            .addInterceptor(networkConnectionIncterceptor)
            .addInterceptor(logging)
            .addInterceptor(interceptor)

        return Retrofit.Builder().client(okHttpClient1.build())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiClient::class.java)

    }

}