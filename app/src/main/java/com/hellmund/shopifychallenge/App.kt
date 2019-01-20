package com.hellmund.shopifychallenge

import android.app.Application
import com.hellmund.shopifychallenge.di.AppComponent
import com.hellmund.shopifychallenge.di.AppModule
import com.hellmund.shopifychallenge.di.DaggerAppComponent
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        buildAppComponent()
        setupPicasso()
    }

    private fun buildAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    private fun setupPicasso() {
        val picasso = Picasso.Builder(this)
            .downloader(OkHttp3Downloader(this, Integer.MAX_VALUE.toLong()))
            .build()

        Picasso.setSingletonInstance(picasso)
    }

}
