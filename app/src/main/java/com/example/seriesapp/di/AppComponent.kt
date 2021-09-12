package com.example.seriesapp.di

import android.content.Context

import com.example.seriesapp.ui.MainActivity
import com.example.seriesapp.ui.MainFragment
import com.example.seriesapp.ui.SplashFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class,ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(popularFragment: SplashFragment)
    fun inject(detailsFragment: MainFragment)

}