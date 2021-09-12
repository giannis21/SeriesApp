package com.example.seriesapp

import android.app.Application

import com.example.seriesapp.di.AppComponent
import com.example.seriesapp.di.DaggerAppComponent

open class MyApplication : Application() {


    // Instance of the AppComponent that will be used by the mainActivity in the project
    val appComponent: AppComponent by lazy {

        // Creates an instance of AppComponent using its Factory constructor
        // i pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(applicationContext)

    }

}
